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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.incidentreport.pivotaltracker.PivotalTrackerService;
import se.vgregion.usdservice.USDService;
import se.vgregion.userfeedback.IncidentReport;
import se.vgregion.userfeedback.IncidentReportService;
import se.vgregion.util.EMailClient;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of the incident report service.
 */
@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

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
    private static final String INCIDENT_REPORT_SERVICE_ADMIN_EMAIL = "tycktill@vgregion.se";

    /**
     * Email reply address.
     */
    private static final String INCIDENT_REPORT_SERVICE_NOREPLY = "IncidentReportService-noreply@vgregion.se";

    /**
     * Email subject when something is not working as expected in Tyck till
     */
    private static final String INCIDENT_REPORT_ERROR_EMAIL_SUBJECT = "*ERROR in IncidentReportService* ";

    /**
     * Email subject
     */
    private static final String INCIDENT_REPORT_EMAIL_SUBJECT = "IncidentReportService message";

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentReportServiceImpl.class);

    @Autowired
    @Qualifier("usdService")
    private USDService usdService;

    @Autowired
    private PivotalTrackerService pivotalTrackerClient;

    /**
     * {@inheritDoc}
     */
    public int reportIncident(IncidentReport ir) {
        int result = 0;
        try {
            cleanupTemporaryFiles();
            String method = ir.getReportMethod();
            if ("usd".equalsIgnoreCase(method)) {
                try {
                    Properties parameters = mapToRequestParameters(ir);
                    List<se.vgregion.userfeedback.Screenshot> ss = ir.getScreenShots();
                    List<File> files = new ArrayList<File>();
                    List<String> filenames = new ArrayList<String>();
                    for (se.vgregion.userfeedback.Screenshot s : ss) {
                        files.add(new File(s.getPath()));
                        filenames.add(s.getFileName());
                    }
                    usdService.createRequest(parameters, ir.getUserId(), files, filenames);
                } catch (Exception e) {
                    LOGGER.error("USD service could not be reached, trying email instead.", e);
                    sendReportByEmail(ir, INCIDENT_REPORT_ERROR_EMAIL_SUBJECT + ":" + "USD-" + e.getMessage());
                }
            } else if ("pivotaltracker".equalsIgnoreCase(method)) {
                try {
                    createUserStory(ir);
                } catch (Exception e) {
                    LOGGER.error("Pivotal tracker could not be reached, trying email instead.", e);
                    sendReportByEmail(ir,
                            INCIDENT_REPORT_ERROR_EMAIL_SUBJECT + ":" + "PivotalTracker-" + e.getMessage());
                }
            } else {
                sendReportByEmail(ir, INCIDENT_REPORT_EMAIL_SUBJECT);
            }
        } catch (Exception e) {
            LOGGER.error("Incident report could not be sent:", e);
            result = -1;
        }
        return result;
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

    private void createUserStory(IncidentReport ir) {
        String applicationName = ir.getApplicationName().replaceAll(" ", "_");
        String projectId = lookupProjectId(applicationName);
        PTStory story = new PTStory();
        story.setName(applicationName + ": IncidentReportService message");
        story.setType("bug");
        story.setProjectId(projectId);
        story.setDescription(ir.toString());
        String url = pivotalTrackerClient.createuserStory(story);

        List<se.vgregion.userfeedback.Screenshot> attachments = ir.getScreenShots();
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
                LOGGER.warn("Pivotal tracker attachments not working", ex);
                // attachments that cannot be sent to Pivotal Tracker must be
                // emailed...
                if (ir.getReportEmail() != null && ir.getReportEmail().length() > 0) {
                    try {
                        ir.setLink("User story: <a href='" + url + "'>" + url + "</a></b>");

                        sendReportByEmail(ir, "Bifogade filer som skall till pivotal tracker.");
                    } catch (MessagingException e) {
                        LOGGER.warn("attachments sending failed ", e);
                    }
                }
            }
        }
    }

    private void sendReportByEmail(IncidentReport ir, String subject) throws MessagingException {
        String reportEmail = ir.getReportEmail();

        if (reportEmail != null && !"".equals(reportEmail)) {
            String body = "";
            String[] reportEmailArray = { reportEmail };
            body += ir.toString().replaceAll(IncidentReport.NEWLINE, "</br>");
            try {
                new EMailClient().postMail(reportEmailArray, ir.getApplicationName() + ":" + subject, "" + body,
                        INCIDENT_REPORT_SERVICE_NOREPLY, ir.getScreenShots());
            } catch (Throwable e1) {
                LOGGER.error("Email submission failed:", e1);
                String[] emailTo = { INCIDENT_REPORT_SERVICE_ADMIN_EMAIL };

                new EMailClient().postMail(emailTo, ir.getApplicationName() + ":"
                        + INCIDENT_REPORT_ERROR_EMAIL_SUBJECT, "" + e1.getMessage() + "\n" + body,
                        INCIDENT_REPORT_SERVICE_NOREPLY, ir.getScreenShots());
            }
        }
    }

    private Properties mapToRequestParameters(IncidentReport ir) {
        Properties p = new Properties();
        p.setProperty("affected_resource", "nr:BF5880E3AF1C8542B2546B93922C25A7");
        p.setProperty("category", "pcat:400023");
        // map to group using application name?
        String appName = ir.getApplicationName().trim().replaceAll(" ", "_");
        String groupHandle = usdService.getUSDGroupHandleForApplicationName(appName);
        p.setProperty("group", groupHandle);
        p.setProperty("impact", "imp:1603");
        p.setProperty("priority", "pri:500");
        p.setProperty("type", "crt:182");
        p.setProperty("urgency", "urg:1100");
        p.setProperty("z_location", "loc:67F817D782E87B45A8298FC5512B6A9C");
        p.setProperty("z_organization", "org:5527E3F8D19F49409036F162493C7DD0");
        if (ir.getPhoneNumber() != null) {
            p.setProperty("z_telefon_nr", ir.getPhoneNumber());
        } else {
            p.setProperty("z_telefon_nr", "N/A");
        }
        StringBuffer descBuf = new StringBuffer();
        descBuf.append(ir.toString() + "\n");
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
}