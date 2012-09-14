package se.vgregion.userfeedback.impl;

import se.vgregion.util.Attachment;
import se.vgregion.util.EMailClient;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class provides a mock email client.
 * 
 * When a PostMail*-method is called the parameters are stored and can be retrieved by calling the corresponding
 * getters. This allows you to inspect the payload that is sent to an {@code EmailClient}.
 * 
 * @author Arakun
 * 
 */
public class EMailClientMock extends EMailClient {

    private List<Attachment> attachments;

    private List<String> recipients;

    private String message;

    @Override
    public void postMailWithAttachments(String[] recipients, String subject, String message, String from,
            List<Attachment> attachments) throws MessagingException {
        this.recipients = Arrays.asList(recipients);
        this.message = message;
        this.attachments = attachments;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sends email to specified recipients with specified content.
     * 
     * @param recipients
     *            A string array containing the recipients.
     * @param subject
     *            Subject of the email.
     * @param message
     *            Body content.
     * @param from
     *            The email appears to come from this address.
     * @throws MessagingException
     *             Thrown if email sending is unsuccessful, eg. if the recipients are invalid.
     */
    @Override
    public void postMail(String[] recipients, String subject, String message, String from)
            throws MessagingException {
        this.recipients = Arrays.asList(recipients);
        this.message = message;
        this.attachments = Collections.emptyList();
    }
}
