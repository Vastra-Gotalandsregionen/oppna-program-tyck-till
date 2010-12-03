package se.vgregion.userfeedback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

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

    @Test
    @Ignore
    public void testReportestReportToPivotaltIncident() throws Exception {
        File tempFile = File.createTempFile("incidentReportTest", "test");
        String appName = "Tyck_till_test_portlet";

        // Add dummy platform data
        PlatformData platform = new PlatformData();
        platform.setBrowser("Lynx");
        platform.setIpAddress("327.0.0.1");
        platform.setOperatingSystem("MS-DOS 5.0");
        platform.setTimeStamp(new Date());
        platform.setUserId("woodyw");

        // Add dummy message data
        FeedbackMessage message = new FeedbackMessage();
        message.setDescription("Det här är ett test av messagedescription-fältet.");
        message.setReportType("Webbplatsens innehåll - Påskägg");

        List<ReportMethod> reportMethods = new ArrayList<FeedbackReport.ReportMethod>();
        reportMethods.add(ReportMethod.pivotal);

        List<UserContact> contactMethods = new ArrayList<UserContact>();
        UserContact contact = new UserContact();
        contact.setContactOption(UserContactOption.email);
        contact.setUserName("Hacke Hackspett");
        contact.setContactMethod("woddyw@warnerbros.com");
        contactMethods.add(contact);

        FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        reportService.reportFeedback(report);
        System.out.println("Reported feedback " + report.toString());
    }

}
