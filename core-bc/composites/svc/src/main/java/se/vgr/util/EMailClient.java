package se.vgr.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import se.vgr.incidentreport.Screenshot;

public class EMailClient {

    private static final String MAILHOST_VGREGION_SE = "mailhost.vgregion.se";

    public void postMail(String recipients[], String subject, String message, String from)
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

    public void postMail(String recipients[], String subject, String message, String from, List<Screenshot> list)
            throws MessagingException {
        boolean debug = false;
        if (list == null || list.size() == 0) {
            postMail(recipients, subject, message, from);
        }
        else {

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
            // msg.setContent(message, "text/html; charset=ISO-8859-1");
            // create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // fill message
            messageBodyPart.setContent(message, "text/html; charset=ISO-8859-1");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            for (Screenshot ss : list) {
                File file = new File(ss.getPath());
                for (int i = 0; i < 10; i++) {
                    if (file.exists()) {
                        System.out.println("File exists:" + file.getAbsolutePath());
                        i = 10;
                    }
                    else {
                        System.out.println("File not found here:" + file.getAbsolutePath());
                        try {

                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(ss.getFileName());
                multipart.addBodyPart(messageBodyPart);
            }
            // Put parts in message
            msg.setContent(multipart);

            // Send the message
            Transport.send(msg);
        }

    }

}
