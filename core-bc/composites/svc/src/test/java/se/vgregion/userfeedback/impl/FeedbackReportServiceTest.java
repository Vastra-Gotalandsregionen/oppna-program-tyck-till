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
 */

package se.vgregion.userfeedback.impl;

import junit.framework.TestCase;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserContact.UserContactOption;
import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.util.Attachment;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

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
    private VelocityEngine velocityEngine;

    @Autowired
    @Resource
    private InputStreamSource attachmentFile;

    private static final String TEST_DESCRIPTION = "Test message";
    private static final String TEST_USER_NAME = "Hacke Hackspett";

    private String TEST_ATTACHMENT_FILENAME = "meow_cat_art.jpg";
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
//        File file = new File("classpath:" + TEST_ATTACHMENT_FILENAME);
        se.vgregion.userfeedback.domain.Attachment attachment = new se.vgregion.userfeedback.domain.Attachment();
        attachment.setFile(getBytesFromFile(attachmentFile));
        attachment.setFilename(TEST_ATTACHMENT_FILENAME);
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
    public byte[] getBytesFromFile(InputStreamSource source) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = source.getInputStream();
        int maxLen = 1024;
        int len = 0;
        byte[] buf = new byte[maxLen];
        while ((len = is.read(buf, 0, maxLen)) != -1) {
           os.write(buf, 0, len);
        }
        os.flush();
        bytes = os.toByteArray();
        is.close();
        os.close();
        return bytes;
    }
}
