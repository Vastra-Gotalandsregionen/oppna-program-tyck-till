/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.userfeedback.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.incidentreport.pivotaltracker.PivotalTrackerService;
import se.vgregion.usdservice.USDService;
import se.vgregion.userfeedback.FeedbackReport;
import se.vgregion.userfeedback.FeedbackReportService;
import se.vgregion.userfeedback.IncidentReport;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.util.EMailClient;

/**
 * Implementation of the incident report service.
 */
@Service("feedbackReportService")
public class FeedbackReportServiceImpl implements FeedbackReportService {

    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MILLISECONDS_PER_SECOND = 1000;

    /**
     * Time in minutes when a file is considered old in the temp file directory, and hence is removed.
     */
    private static final int TEMP_FILES_OLDMINUTES = 15;

    @Autowired
    @Qualifier("pivotalTrackerMappings")
    private Properties pivotalTrackerMappings;

    /**
     * Reciever of messages when the regular report recievers can´t be reached.
     */
    private static final String FEEDBACK_REPORT_SERVICE_ADMIN_EMAIL = "tycktill@vgregion.se";

    /**
     * Email reply address.
     */
    private static final String FEEDBACK_REPORT_SERVICE_NOREPLY = "FeedbackReportService-noreply@vgregion.se";

    /**
     * Email subject when something is not working as expected in Tyck till
     */
    private static final String FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT = "*ERROR in FeedbackReportService* ";

    /**
     * Email subject
     */
    private static final String FEEDBACK_REPORT_EMAIL_SUBJECT = "FeedbackReportService message";

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackReportServiceImpl.class);

    @Autowired
    @Qualifier("usdService")
    private USDService usdService;

    @Autowired
    private PivotalTrackerService pivotalTrackerClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public int reportFeedback(FeedbackReport report) {
        int result = 0;
        try {
            cleanupTemporaryFiles();
            List<FeedbackReport.ReportMethod> methods = report.getReportMethods();
            for (FeedbackReport.ReportMethod method : methods) {
                switch (method) {
                    case email:
                        result = reportByEmail(report);
                        break;
                    case pivotal:
                        result = registerInPivotal(report);
                        break;
                    case usd:
                        result = registerInUsd(report);
                        break;
                    default:
                        result = -1;
                        throw new RuntimeException("Unrecognized report method " + method);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Incident report could not be sent:", e);
            result = -1;
        }
        return result;
    }

    private int registerInUsd(FeedbackReport report) {
        try {
            Properties parameters = mapToRequestParameters(report);
            List<se.vgregion.userfeedback.Screenshot> ss = report.getScreenShots();
            List<File> files = new ArrayList<File>();
            List<String> filenames = new ArrayList<String>();
            for (se.vgregion.userfeedback.Screenshot s : ss) {
                files.add(new File(s.getPath()));
                filenames.add(s.getFileName());
            }
            usdService.createRequest(parameters, report.getUserPlatform().getUserId(), files, filenames);
        } catch (Exception e) {
            LOGGER.error("USD service could not be reached, trying email instead.", e);
            try {
                sendReportByEmail(report, FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT + ":" + "USD-" + e.getMessage());
            } catch (MessagingException me) {
                return -1;
            }
        }
        return 0;
    }

    private int registerInPivotal(FeedbackReport report) {
        try {
            createUserStory(report);
        } catch (Exception e) {
            LOGGER.error("Pivotal tracker could not be reached, trying email instead.", e);
            try {
                sendReportByEmail(report, FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT + ":" + "PivotalTracker-"
                        + e.getMessage());
            } catch (MessagingException me) {
                return -1;
            }
        }
        return 0;
    }

    private int reportByEmail(FeedbackReport report) {
        try {
            sendReportByEmail(report, FEEDBACK_REPORT_EMAIL_SUBJECT);
        } catch (MessagingException me) {
            return -1;
        }
        return 0;
    }

    private void cleanupTemporaryFiles() {
        // System.out.println("Deleting temp files..");
        Date now = new Date();
        try {
            File f = File.createTempFile("cleanup", "tmp");
            File tempDir = f.getParentFile();
            File[] tempfiles = tempDir.listFiles();
            for (int i = 0; i < tempfiles.length; i++) {
                // Remove files older than
                if (tempfiles[i].lastModified() + TEMP_FILES_OLDMINUTES * SECONDS_PER_MINUTE
                        * MILLISECONDS_PER_SECOND < now.getTime()) {
                    tempfiles[i].delete();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void createUserStory(FeedbackReport report) {
        String applicationName = report.getMessage().getTrackerCategory().replaceAll(" ", "_");
        String projectId = lookupProjectId(applicationName);
        PTStory story = new PTStory();
        story.setName(applicationName + ": IncidentReportService message");
        story.setType("bug");
        story.setProjectId(projectId);
        story.setDescription(report.toString());
        String url = pivotalTrackerClient.createuserStory(story);

        List<se.vgregion.userfeedback.Screenshot> attachments = report.getScreenShots();
        List<File> atts = new ArrayList<File>();
        for (int i = 0; i < attachments.size(); i++) {
            atts.add(new File(attachments.get(i).getPath()));
        }
        story.setAttachments(atts);
        List<String> attNames = new ArrayList<String>();
        for (int i = 0; i < attachments.size(); i++) {
            attNames.add(attachments.get(i).getFileName());
        }
        story.setAttachmentNames(attNames);
        if (url != null && attachments != null && attachments.size() > 0) {
            try {
                pivotalTrackerClient.addAttachmentToStory(story.getProjectId(), story);
            } catch (Exception ex) {
                LOGGER.error("Pivotal tracker attachments not working", ex);
            }
        }
    }

    private void sendReportByEmail(FeedbackReport report, String subject) throws MessagingException {
        String reportEmail = report.getReportEmail();

        if (reportEmail != null && !"".equals(reportEmail)) {
            String body = "";
            String[] reportEmailArray = { reportEmail };
            body += report.toString().replaceAll(IncidentReport.NEWLINE, "</br>");
            try {
                new EMailClient().postMail(reportEmailArray, report.getMessage().getTrackerCategory() + ":"
                        + subject, "" + body, FEEDBACK_REPORT_SERVICE_NOREPLY, report.getScreenShots());
            } catch (Throwable e1) {
                LOGGER.error("Email submission failed:", e1);
                String[] emailTo = { FEEDBACK_REPORT_SERVICE_ADMIN_EMAIL };

                new EMailClient().postMail(emailTo, report.getMessage().getTrackerCategory() + ":"
                        + FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT, "" + e1.getMessage() + "\n" + body,
                        FEEDBACK_REPORT_SERVICE_NOREPLY, report.getScreenShots());
            }
        }
    }

    private Properties mapToRequestParameters(FeedbackReport report) {
        Properties p = new Properties();
        p.setProperty("affected_resource", "nr:BF5880E3AF1C8542B2546B93922C25A7");
        p.setProperty("category", "pcat:400023");
        // map to group using application name?
        String trackerCategory = report.getMessage().getTrackerCategory().trim().replaceAll(" ", "_");
        String groupHandle = usdService.getUSDGroupHandleForApplicationName(trackerCategory);
        p.setProperty("group", groupHandle);
        p.setProperty("impact", "imp:1603");
        p.setProperty("priority", "pri:500");
        p.setProperty("type", "crt:182");
        p.setProperty("urgency", "urg:1100");
        p.setProperty("z_location", "loc:67F817D782E87B45A8298FC5512B6A9C");
        p.setProperty("z_organization", "org:5527E3F8D19F49409036F162493C7DD0");
        p.setProperty("z_telefon_nr", lookupPhonenumber(report));

        StringBuffer descBuf = new StringBuffer();
        descBuf.append(report.toString() + "\n");
        p.setProperty("description", descBuf.toString());
        return p;
    }

    private String lookupProjectId(String applicationName) {
        String result = pivotalTrackerMappings.getProperty(applicationName);
        if (result == null || result.length() == 0) {
            result = applicationName;
        }
        return result;
    }

    private String lookupPhonenumber(FeedbackReport report) {
        // Fetch user phone number
        String userPhonenumber = "N/A";
        for (UserContact contact : report.getUserContactMethod()) {
            if (contact.getContactOption() == UserContact.UserContactOption.telephone) {
                userPhonenumber = contact.getContactMethod();
            }
        }
        return userPhonenumber;
    }
}