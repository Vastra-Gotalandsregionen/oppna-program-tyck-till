package se.vgregion.userfeedback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserContact.UserContactOption;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/services-config.xml")
public class FeedbackIntegrationTest extends TestCase {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private FeedbackReportService reportService;

    FeedbackMessage message;
    PlatformData platform;
    List<ReportMethod> reportMethods;
    List<UserContact> contactMethods;
    Screenshot screenShot;

    @Before
    public void setupFeedback() throws Exception {
        // Add dummy platform data
        platform = new PlatformData();
        platform.setBrowser("Lynx");
        platform.setIpAddress("327.0.0.1");
        platform.setOperatingSystem("MS-DOS 5.0");
        platform.setTimeStamp(new Date());
        platform.setUserId("woodyw");

        // Add dummy message data
        message = new FeedbackMessage();
        message.setDescription("Det här är ett test av messagedescription-fältet.");
        message.setReportType("Webbplatsens innehåll - Påskägg");
        message.setTrackerCategory("Tyck_till_test_portlet");

        reportMethods = new ArrayList<FeedbackReport.ReportMethod>();

        contactMethods = new ArrayList<UserContact>();
        UserContact contact = new UserContact();
        contact.setContactOption(UserContactOption.email);
        contact.setUserName("Hacke Hackspett");
        contact.setContactMethod("woddyw@warnerbros.com");
        contactMethods.add(contact);

        File file = new File("/chairman-meow-little-red.jpg");
        screenShot = new Screenshot();
        screenShot.setPath(file.getCanonicalPath());
        screenShot.setFileName(file.getName());
    }

    @Test
    @Ignore
    public void testReportToPivotal() throws Exception {
        reportMethods.add(ReportMethod.pivotal);
        message.setTrackerCategory("Tyck_till_test_portlet");
        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        report.addScreenShot(screenShot);
        // reportService.reportFeedback(report);
    }

    @Test
    @Ignore
    public void testReportToUSD() throws Exception {
        reportMethods.add(ReportMethod.usd);
        message.setTrackerCategory("Tyck_till_test_portlet");
        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        report.addScreenShot(screenShot);
        // reportService.reportFeedback(report);
    }

    @Test
    @Ignore
    public void testReportToEmail() throws Exception {
        reportMethods.add(ReportMethod.email);
        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        report.addScreenShot(screenShot);
        report.getMessage().setReportEmail("test_account@nowhere.com");
        // reportService.reportFeedback(report);
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

}
