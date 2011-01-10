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

package se.vgregion.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for email sending support.
 */
public class EMailClient {

    /**
     * 
     */
    private static final int FILE_RETRY_ATTEMPTS = 10;
    private static final String MAILHOST_VGREGION_SE = "mailhost.vgregion.se";
    private static final int SLEEP_TIME = 1000;

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
    public void postMail(String[] recipients, String subject, String message, String from)
            throws MessagingException {
        boolean debug = false;

        // Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", MAILHOST_VGREGION_SE);

        // create some properties and get the default Session
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        // Optional : You can also set your custom headers in the Email if you
        // Want
        // msg.addHeader("MyHeaderName", "myHeaderValue");

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=ISO-8859-1");

        Transport.send(msg);
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
     * @param attachments
     *            List of file attachments.
     * @throws MessagingException
     *             Thrown if email sending is unsuccessful, eg. if the recipients are invalid.
     */
    public void postMailWithAttachments(String[] recipients, String subject, String message, String from,
            List<Attachment> attachments) throws MessagingException {
        if (attachments == null || attachments.size() == 0) {
            postMail(recipients, subject, message, from);
        } else {

            // Set the host smtp address
            Properties props = new Properties();
            props.put("mail.smtp.host", MAILHOST_VGREGION_SE);

            // create some properties and get the default Session
            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(debug);

            // create a message
            Message msg = new MimeMessage(session);

            // set the from and to address
            InternetAddress addressFrom = new InternetAddress(from);
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addressTo[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, addressTo);

            // Optional : You can also set your custom headers in the Email if you
            // Want
            // msg.addHeader("MyHeaderName", "myHeaderValue");

            // Setting the Subject and Content Type
            msg.setSubject(subject);
            // msg.setContent(message, "text/html; charset=ISO-8859-1");
            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // fill message
            messageBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            for (Attachment attachment : attachments) {

                MimeBodyPart attachmentPart = new MimeBodyPart();
                DataSource source;
                try {
                    source = new ByteArrayDataSource(attachment.getData(), "application/octet-stream");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(attachment.getFilename());
                multipart.addBodyPart(attachmentPart);
            }
            // Put parts in message
            msg.setContent(multipart);

            // Send the message
            Transport.send(msg);
        }
    }
}
