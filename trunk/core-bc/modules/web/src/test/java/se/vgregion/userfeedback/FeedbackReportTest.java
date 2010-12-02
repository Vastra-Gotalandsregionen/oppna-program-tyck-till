package se.vgregion.userfeedback;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;

public class FeedbackReportTest extends TestCase {

    @Test
    @Ignore
    public void testReportestReportToPivotaltIncident() throws Exception {
        File tempFile = File.createTempFile("incidentReportTest", "test");

        String appName = "Tyck_till_test_portlet";

        PlatformData platform = new PlatformData();
        platform.setBrowser("Lynx");
        platform.setIpAddress("327.0.0.1");

        FeedbackMessage message = new FeedbackMessage();
        List<ReportMethod> reportMethods;
        List<UserContact> contactMethods;

        // FeedbackReport report = new FeedbackReport(message, reportMethods, contactMethods, platform);
        //
        // // ==================================================================
        // // Test Pivotal Tracker reporting
        // report.set ReportMethod("pivotaltracker");
        // PivotalTrackerService pivotalTrackerClientMock = mock(PivotalTrackerService.class);
        // Properties pivotalTrackerMappingsMock = mock(Properties.class);
        // ReflectionTestUtils.setField(reportService, "pivotalTrackerClient", pivotalTrackerClientMock,
        // PivotalTrackerService.class);
        // ReflectionTestUtils.setField(reportService, "pivotalTrackerMappings", pivotalTrackerMappingsMock,
        // Properties.class);
        //
        // when(pivotalTrackerMappingsMock.getProperty(appName)).thenReturn("35420");
        // when(pivotalTrackerClientMock.createuserStory(any(PTStory.class))).thenReturn("http://fakeURL");
        // reportService.reportIncident(ir);
        //
        // // same as above, test the method when an exception is thrown
        // ir.setApplicationName(null);
        // reportService.reportIncident(ir);
        // // need to set a proper application name so that the rest of the tests run
        // ir.setApplicationName(appName);
        // // ==================================================================
        //
        // // ==================================================================
        // // Test when no method is specified, should indicate email report
        // ir.setReportMethod("");
        // reportService.reportIncident(ir);
        // verifyPrivate(reportService).invoke("sendReportByEmail", ir, "*ERROR in IncidentReportService* ");
        //
        // // test the method when an exception is thrown
        // ir.setApplicationName(null);
        // reportService.reportIncident(ir);
        // // need to set a proper application name so that the rest of the tests run
        // ir.setApplicationName(appName);
        // // ==================================================================
        //
        // // ==================================================================
        // // finally, cause an error at the top of the reportIncident method
        // ir = null;
        // reportService.reportIncident(ir);
        // // ==================================================================
        // }
        //
    }

}
