package se.vgregion.userfeedback.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserContact.UserContactOption;
import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.util.Attachment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-services-config.xml")
public class FeedbackReportServiceTest extends TestCase {

    private ApplicationContext applicationContext;

    @Autowired
    private FeedbackReportServiceImpl reportService;

    @Autowired
    private USDServiceMock usdServiceMock;
    @Autowired
    private PivotalServiceMock pivotalServiceMock;
    @Autowired
    private EMailClientMock emailServiceMock;

    @Autowired
    VelocityEngine velocityEngine;

    private static final String TEST_DESCRIPTION = "Test message";
    private static final String TEST_USER_NAME = "Hacke Hackspett";
    private static final String TEST_ATTACHMENT_FILENAME = "chairman-meow-little-red.jpg";
    private static final String TEST_EMAIL = "woodyw@woodpeckers.rus";

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
        File file = new File("/" + TEST_ATTACHMENT_FILENAME);
        se.vgregion.userfeedback.domain.Attachment attachment = new se.vgregion.userfeedback.domain.Attachment();
        attachment.setFile(getBytesFromFile(file));
        attachment.setFilename(file.getName());
        Set<se.vgregion.userfeedback.domain.Attachment> attachments = new HashSet<se.vgregion.userfeedback.domain.Attachment>();
        attachments.add(attachment);

        // Set user contact info
        UserContact userContact = new UserContact();
        userContact.setUserName(TEST_USER_NAME);
        userContact.setContactMethod("woodyw@nowhere.com");
        userContact.setShouldContactUser(true);
        userContact.setContactOption(UserContactOption.email);

        feedback.setPlatformData(platform);
        feedback.setUserContact(userContact);
        feedback.setCaseMessage(TEST_DESCRIPTION);
        feedback.setAttachments(attachments);
    }

    @Test
    public void testReportToPivotal() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setPivotal("35420");
        caseBackend.setActivePivotal(true);
        feedback.setCaseBackend(caseBackend);
        reportService.reportFeedback(feedback);

        // Test that report template gets applied.
        PTStory story = pivotalServiceMock.getUserStory();
        String description = story.getDescription();
        assertTrue(description.indexOf(TEST_USER_NAME) > 0);
        assertTrue(description.indexOf("Kontakta användaren via:") > 0);

        // Test that attachments are added.
        Map<String, Attachment> attachments = pivotalServiceMock.getAttachments();
        assertTrue(attachments.size() == 1);
    }

    @Test
    public void testReportToUSD() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setUsd("Tyck_till_test_portlet");
        caseBackend.setActiveUsd(true);
        feedback.setCaseBackend(caseBackend);
        reportService.reportFeedback(feedback);

        // Check web service request parameters
        Properties requestParameters = usdServiceMock.getRequestParameters();
        assertEquals(USDServiceMock.getTestUsdHandle(), requestParameters.getProperty("group"));
        assertEquals("loc:67F817D782E87B45A8298FC5512B6A9C", requestParameters.getProperty("z_location"));

        // Test that attachments are added.
        Collection<Attachment> attachments = usdServiceMock.getAttachments();
        assertTrue(attachments.size() == 1);
    }

    @Test
    public void testReportToEmail() throws Exception {
        Backend caseBackend = new Backend();
        caseBackend.setMbox(TEST_EMAIL);
        caseBackend.setActiveMbox(true);
        feedback.setCaseBackend(caseBackend);
        reportService.reportFeedback(feedback);

        // Check recipient email
        List<String> recipients = emailServiceMock.getRecipients();
        String recipient = recipients.get(0);
        assertEquals(TEST_EMAIL, recipient);

        // Check attachments
        List<Attachment> attachments = emailServiceMock.getAttachments();
        Attachment attachment = attachments.get(0);
        assertEquals(TEST_ATTACHMENT_FILENAME, attachment.getFilename());
    }

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
