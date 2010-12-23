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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.incidentreport.pivotaltracker.PivotalTrackerService;
import se.vgregion.usdservice.USDService;
import se.vgregion.userfeedback.FeedbackReportService;
import se.vgregion.userfeedback.domain.Attachment;
import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.util.EMailClient;

import javax.mail.MessagingException;
import javax.persistence.Transient;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Implementation of the incident report service.
 */
@Service("feedbackReportService")
public class FeedbackReportServiceImpl implements FeedbackReportService {

    @Autowired
    @Transient
    private VelocityEngine velocityEngine;

    /**
     * Template for E-mail message.
     */
    private static final String EMAIL_TEMPLATE = "velocity/tycktill-email-report.vm";

    /**
     * Template for Pivotal tracker message.
     */
    private static final String PIVOTAL_TEMPLATE = "velocity/tycktill-pivotal-report.vm";

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
    private static final String FEEDBACK_REPORT_SERVICE_NOREPLY = "tycktill-noreply@vgregion.se";

    /**
     * Email subject when something is not working as expected in Tyck till
     */
    private static final String FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT = "*ERROR in FeedbackReportService* ";

    /**
     * Email subject
     */
    private static final String FEEDBACK_REPORT_EMAIL_SUBJECT = "TyckTill message";

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
    public int reportFeedback(UserFeedback report) {
        int result = 0;

        Backend backend = report.getCaseBackend();
        try {

            if (backend.isActivePivotal()) {
                registerInPivotal(report);
            }
//            if (backend.isActiveUsd()) {
//                registerInUsd(report);
//            }
            if (backend.isActiveMbox()) {
                reportByEmail(report);
            }

        } catch (Exception e) {
            LOGGER.error("Incident report could not be sent:", e);
            result = -1;
        }
        return result;
    }

    private int registerInUsd(UserFeedback report) {
        try {
            Properties parameters = mapToRequestParameters(report);

            Collection<se.vgregion.util.Attachment> attachments = mapAttachments(report);

            usdService.createRequest(parameters, report.getPlatformData().getUserId(), attachments);

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

    private int registerInPivotal(UserFeedback report) {
        try {
            createUserStory(report);

            LOGGER.info("Successfully reported to pivotal");
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

    private int reportByEmail(UserFeedback report) {
        try {
            sendReportByEmail(report, FEEDBACK_REPORT_EMAIL_SUBJECT);
            LOGGER.info("Successfully reported by email");
        } catch (MessagingException me) {

            System.out.println("Failed report by email" + me.toString());
            return -1;
        }
        return 0;
    }

    /**
     * Make a new Velocity context and populate it from a {@code UserFeedback} instance.
     * 
     * @param report
     *            .
     * @return .
     */
    private static VelocityContext makeVelocityContext(UserFeedback report) {
        VelocityContext context = new VelocityContext();
        context.put("date", new DateTool());
        context.put("feedback", report);
        return context;
    }

    /**
     * Retrieve a template, merge it with the {@code UserFeedback} data and return the result as a String.
     * 
     * @param template
     *            Name of the Velocity template.
     * @param report
     *            .
     * @return .
     */
    private String makeReport(String template, UserFeedback report) {
        StringWriter writer = new StringWriter();
        try {
            Template t = velocityEngine.getTemplate(template, "UTF-8");
            VelocityContext context = makeVelocityContext(report);

            t.merge(context, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    private void createUserStory(UserFeedback report) {
        String projectId = report.getCaseBackend().getPivotal();
        PTStory story = new PTStory();
        story.setName(projectId + ": TyckTill message");
        story.setType("chore");
        story.setProjectId(projectId);

        String description = makeReport(PIVOTAL_TEMPLATE, report);
        story.setDescription(description);

        String url = pivotalTrackerClient.createuserStory(story);
        report.setHyperLink(url);

        List<se.vgregion.util.Attachment> attachments = mapAttachments(report);
        story.setAttachments(attachments);

        if (url != null && attachments.size() > 0) {
            try {
                pivotalTrackerClient.addAttachmentToStory(story.getProjectId(), story);
            } catch (Exception ex) {
                LOGGER.error("Pivotal tracker attachments not working", ex);
            }
        }
    }

    private List<se.vgregion.util.Attachment> mapAttachments(UserFeedback report) {
        Collection<Attachment> byteAttachments = report.getAttachments();

        List<se.vgregion.util.Attachment> attachments = new ArrayList<se.vgregion.util.Attachment>();
        for (Attachment byteAttachment : byteAttachments) {
            se.vgregion.util.Attachment attachment = new se.vgregion.util.Attachment();

            attachment.setFilename(byteAttachment.getFilename());
            attachment.setFileLength(byteAttachment.getFile().length);
            attachment.setData(new ByteArrayInputStream(byteAttachment.getFile()));
            attachments.add(attachment);
        }
        return attachments;
    }

    private void sendReportByEmail(UserFeedback report, String subject) throws MessagingException {
        String reportEmail = report.getCaseBackend().getMbox();

        if (reportEmail != null && !"".equals(reportEmail)) {
            String description = makeReport(EMAIL_TEMPLATE, report);
            String[] reportEmailArray = { reportEmail };

            try {
                new EMailClient().postMailWithAttachments(reportEmailArray, subject, description,
                        FEEDBACK_REPORT_SERVICE_NOREPLY, mapAttachments(report));
            } catch (MessagingException e1) {
                LOGGER.error("Email submission failed:", e1);
                String[] emailTo = { FEEDBACK_REPORT_SERVICE_ADMIN_EMAIL };

                new EMailClient().postMailWithAttachments(emailTo, FEEDBACK_REPORT_ERROR_EMAIL_SUBJECT, ""
                        + e1.getMessage() + "\n" + description, FEEDBACK_REPORT_SERVICE_NOREPLY,
                        mapAttachments(report));
            }
        }
    }

    private Properties mapToRequestParameters(UserFeedback report) {
        Properties p = new Properties();
        p.setProperty("affected_resource", "nr:BF5880E3AF1C8542B2546B93922C25A7");
        p.setProperty("category", "pcat:400023");
        // // map tracker category to USD group.
        String trackerCategory = report.getCaseBackend().getUsd();
        String groupHandle = usdService.getUSDGroupHandleForApplicationName(trackerCategory);
        p.setProperty("group", groupHandle);
        p.setProperty("impact", "imp:1603");
        p.setProperty("priority", "pri:500");
        p.setProperty("type", "crt:182");
        p.setProperty("urgency", "urg:1100");
        p.setProperty("z_location", "loc:67F817D782E87B45A8298FC5512B6A9C");
        p.setProperty("z_organization", "org:5527E3F8D19F49409036F162493C7DD0");
        p.setProperty("z_telefon_nr", lookupPhonenumber(report));
        p.setProperty("time_spent", "0");

        StringBuffer descBuf = new StringBuffer(report.toString());
        descBuf.append("\n");
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

    private String lookupPhonenumber(UserFeedback report) {
        // Fetch user phone number
        String userPhonenumber = "N/A";
        UserContact contact = report.getUserContact();
        if (contact.getContactOption() == UserContact.UserContactOption.telephone) {
            userPhonenumber = contact.getContactMethod();
        }
        return userPhonenumber;
    }
}