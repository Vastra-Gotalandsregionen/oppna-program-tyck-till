package se.vgregion.userfeedback.impl;

import java.util.List;

import javax.mail.MessagingException;

import se.vgregion.userfeedback.Screenshot;
import se.vgregion.util.Attachment;
import se.vgregion.util.EMailClient;

public class EMailClientMock extends EMailClient {

    /**
     * Sends email to specified recipients with specified content, including attachment support.
     * 
     * @param recipients
     *            A string array containing the recipients.
     * @param subject
     *            Subject of the email.
     * @param message
     *            Body content.
     * @param from
     *            The email appears to come from this address.
     * @param list
     *            List of file attachments.
     * @throws MessagingException
     *             Thrown if email sending is unsuccessful, eg. if the recipients are invalid.
     */
    @Override
    public void postMailWithAttachments(String[] recipients, String subject, String message, String from,
            List<Attachment> attachments) throws MessagingException {
        throw new UnsupportedOperationException("TODO: Implement this method");
    }

    /**
     * Sends email to specified recipients with specified content, including attachment support.
     * 
     * @param recipients
     *            A string array containing the recipients.
     * @param subject
     *            Subject of the email.
     * @param message
     *            Body content.
     * @param from
     *            The email appears to come from this address.
     * @param list
     *            List of file attachments.
     * @throws MessagingException
     *             Thrown if email sending is unsuccessful, eg. if the recipients are invalid.
     */
    @Override
    public void postMail(String[] recipients, String subject, String message, String from, List<Screenshot> list)
            throws MessagingException {
        throw new UnsupportedOperationException("TODO: Implement this method");
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
        throw new UnsupportedOperationException("TODO: Implement this method");
    }
}
