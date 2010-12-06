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
public class FeedbackReportTest extends TestCase {

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
        reportService.reportFeedback(report);
    }

    @Test
    @Ignore
    public void testReportToUSD() throws Exception {
        reportMethods.add(ReportMethod.usd);
        message.setTrackerCategory("Tyck_till_test_portlet");
        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        report.addScreenShot(screenShot);
        reportService.reportFeedback(report);
    }

    @Test
    @Ignore
    public void testReportToEmail() throws Exception {
        reportMethods.add(ReportMethod.email);
        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        report.addScreenShot(screenShot);
        report.getMessage().setReportEmail("test_account@nowhere.com");
        reportService.reportFeedback(report);
    }
}
