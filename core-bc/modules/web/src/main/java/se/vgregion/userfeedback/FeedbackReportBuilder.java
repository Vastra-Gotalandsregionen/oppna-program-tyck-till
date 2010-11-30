package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.FeedbackReport.UserContactMethod;
import se.vgregion.userfeedback.domain.UserFeedback;

/**
 * This is a utility class for creating a {@code FeedbackReport} from the data in a {@code UserFeedback} instance.
 * 
 * @author Arakun
 * 
 */
public class FeedbackReportBuilder {

    private FeedbackMessage message = null;
    private PlatformData userPlatform = null;
    private Map<UserContactMethod, Object> userContactOptions = null;
    private List<ReportMethod> reportMethods = null;

    public FeedbackReport buildFeedbackReport(UserFeedback feedback) {

        setUserPlatform();
        setReportMethods();
        setUserContactMethods();
        setUserMessage();

        FeedbackReport report = new FeedbackReport(this.message, this.reportMethods, this.userContactOptions,
                this.userPlatform);
        return null;
    }

    private void setUserMessage() {
        FeedbackMessage message = new FeedbackMessage();
        message.setDescription("Hello reporting system!");
        this.message = message;

    }

    private void setUserPlatform() {
        PlatformData platform = new PlatformData();
        platform.setBrowser("Lynx 2.8.7");
        platform.setOperatingSystem("Solaris");
        platform.setIpAddress("127.0.0.1");
        platform.setTimeStamp("2012-01-01");
        platform.setUserId("Admin");
        platform.setReferer("Important person");
        this.userPlatform = platform;
    }

    private void setUserContactMethods() {
        Map<UserContactMethod, Object> contactMethods = new TreeMap<FeedbackReport.UserContactMethod, Object>();

        this.userContactOptions = contactMethods;
    }

    private void setReportMethods() {
        List<ReportMethod> reportMethods = new ArrayList<FeedbackReport.ReportMethod>();
        this.reportMethods = reportMethods;
    }
}
