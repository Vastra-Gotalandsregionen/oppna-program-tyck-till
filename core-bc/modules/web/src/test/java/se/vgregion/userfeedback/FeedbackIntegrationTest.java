package se.vgregion.userfeedback;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserContact.UserContactOption;
import se.vgregion.userfeedback.domain.UserFeedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/services-config.xml")
public class FeedbackIntegrationTest extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FeedbackReportService reportService;

    UserFeedback feedback;

    @Before
    public void setupFeedback() throws Exception {
        feedback = new UserFeedback();

        // Add dummy platform data
        PlatformData platform = new PlatformData();
        platform.setBrowser("Lynx");
        platform.setIpAddress("327.0.0.1");
        platform.setOperatingSystem("MS-DOS 5.0");
        platform.setTimeStamp(new Date());
        platform.setUserId("woodyw");

        // Add attachment
        File file = new File("/chairman-meow-little-red.jpg");
        se.vgregion.userfeedback.domain.Attachment attachment = new se.vgregion.userfeedback.domain.Attachment();
        attachment.setFile(getBytesFromFile(file));
        attachment.setFilename(file.getName());
        Set<se.vgregion.userfeedback.domain.Attachment> attachments = new HashSet<se.vgregion.userfeedback.domain.Attachment>();
        attachments.add(attachment);

        // Set user contact info
        UserContact userContact = new UserContact();
        userContact.setUserName("Hacke Hackspett");
        userContact.setContactMethod("woodyw@nowhere.com");
        userContact.setShouldContactUser(true);
        userContact.setContactOption(UserContactOption.email);

        feedback.setPlatformData(platform);
        feedback.setUserContact(userContact);
        feedback.setMessage("Test message.");
        feedback.setAttachments(attachments);
    }

    @Test
    @Ignore
    public void testReportToPivotal() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setPivotal("35420");
        feedback.setCaseBackend(caseBackend);
        reportService.reportFeedback(feedback);
    }

    @Test
    @Ignore
    public void testReportToUSD() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setUsd("35420");
        feedback.setCaseBackend(caseBackend);
        // reportService.reportFeedback(report);
    }

    @Test
    @Ignore
    public void testReportToEmail() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setMbox("arakun@gmail.com");
        feedback.setCaseBackend(caseBackend);
        reportService.reportFeedback(feedback);
    }

    // private String incidentReport1 =
    // "<incidentReport>" + "<reportType>error</reportType>" + "<errorType>errorMessage</errorType>"
    // + "<timestamp>2008-08-08 10:22:22:222</timestamp>" + "<browser>Mozilla</browser>"
    // + "<defaultErrorMessage>defaultErrorMessage</defaultErrorMessage>"
    // + "<description>description</description>" + "<feedback>"
    // + " <sendFeedback>true</sendFeedback>" + "  <feedbackBySms activated=\"false\">"
    // + "     <phoneNumber>070-666666</phoneNumber>" + "  </feedbackBySms>"
    // + " <feedbackByPhone activated=\"false\">" + "      <phoneNumber>070-555555</phoneNumber>"
    // + " </feedbackByPhone>" + " <feedbackByMail activated=\"true\">"
    // + "         <email>john.doe@domain.com</email>" + " </feedbackByMail>" + "</feedback>"
    // + "<screenShot>" + "    <sendScreenShot>true</sendScreenShot>" + "  <files>"
    // + "     <file filename=\"\" mediatype=\"\"/>" + "   </files>" + "</screenShot>  "
    // + "</incidentReport>";
    //
    // private String incidentReport2 =
    // "<incidentReport>"
    // + "<reportType>error</reportType>"
    // + "<errorType>errorMessage</errorType>"
    // + "<timestamp>2008-08-08 10:22:22:222</timestamp>"
    // + "<browser>Mozilla</browser>"
    // + "<defaultErrorMessage>defaultErrorMessage</defaultErrorMessage>"
    // + "<description>description</description>"
    // + "<feedback>"
    // + " <sendFeedback>true</sendFeedback>"
    // + "     <feedbackBySms activated=\"true\">"
    // + "     <phoneNumber>070-666666</phoneNumber>"
    // + " </feedbackBySms>"
    // + " <feedbackByPhone activated=\"true\">"
    // + "     <phoneNumber>070-555555</phoneNumber>"
    // + " </feedbackByPhone>"
    // + " <feedbackByMail activated=\"false\">"
    // + "         <email>john.doe@domain.com</email>"
    // + " </feedbackByMail>"
    // + "</feedback>"
    // + "<screenShot>"
    // + " <sendScreenShot>true</sendScreenShot>"
    // + " <files>"
    // +
    // "         <file filename=\"DSC00059.JPG\" mediatype=\"image/jpeg\">file:/tmp/xforms_upload_24990.tmp</file>"
    // +
    // "         <file filename=\"DSC00060.JPG\" mediatype=\"image/jpeg\">file:/tmp/xforms_upload_24991.tmp</file>"
    // + " </files>" + "</screenShot>  " + "</incidentReport>";
    //
    // @SuppressWarnings("deprecation")
    // @Test
    // public void testReadBasicIncidentReport() throws Exception {
    // IncidentReportReader reader = new IncidentReportReader();
    // IncidentReport ir = reader.parseIncidentReport(new StringBufferInputStream(incidentReport1));
    // assertNotNull(ir);
    //
    // assertEquals("Detta fungerar inte", ir.getReportType());
    // assertEquals(1, ir.getErrorTypes().size());
    // assertEquals("Felmeddelande", ir.getErrorTypes().get(0));
    // // assertTrue(ir.getBrowser().indexOf("Mozilla") > -1);
    // assertEquals("defaultErrorMessage", ir.getDefaultErrorMessage());
    // assertEquals("description", ir.getDescription());
    // assertTrue(ir.isSendFeedback());
    // assertTrue(ir.isFeedbackByMail());
    // assertEquals("john.doe@domain.com", ir.getEmailAddress());
    // assertFalse(ir.isFeedbackByPhone());
    // assertEquals(null, ir.getPhoneNumber());
    // assertFalse(ir.isFeedbackBySms());
    // assertEquals(null, ir.getSmsPhoneNumber());
    // assertTrue(ir.isSendScreenShot());
    // assertEquals(0, ir.getScreenShots().size());
    //
    // }
    //
    // @SuppressWarnings("deprecation")
    // @Test
    // public void testReadIncidentReportWithFiles() throws Exception {
    // IncidentReportReader reader = new IncidentReportReader();
    // IncidentReport ir = reader.parseIncidentReport(new StringBufferInputStream(incidentReport2));
    // assertNotNull(ir);
    //
    // assertEquals("Detta fungerar inte", ir.getReportType());
    // assertEquals(1, ir.getErrorTypes().size());
    // assertEquals("Felmeddelande", ir.getErrorTypes().get(0));
    // // assertEquals("Mozilla", ir.getBrowser());
    // assertEquals("defaultErrorMessage", ir.getDefaultErrorMessage());
    // assertEquals("description", ir.getDescription());
    // assertTrue(ir.isSendFeedback());
    // assertFalse(ir.isFeedbackByMail());
    // assertEquals(null, ir.getEmailAddress());
    // assertTrue(ir.isFeedbackByPhone());
    // assertEquals("070-555555", ir.getPhoneNumber());
    // assertTrue(ir.isFeedbackBySms());
    // assertEquals("070-666666", ir.getSmsPhoneNumber());
    // assertTrue(ir.isSendScreenShot());
    // assertEquals(2, ir.getScreenShots().size());
    //
    // }

    // Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
