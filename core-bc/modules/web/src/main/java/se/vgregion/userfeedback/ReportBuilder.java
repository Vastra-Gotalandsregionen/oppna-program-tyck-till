package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.FeedbackReport.UserContactMethod;
import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.util.UserAgentUtils;

/**
 * This is a utility class for creating a {@code FeedbackReport} from the data in a {@code UserFeedback} instance.
 * 
 * @author Arakun
 * 
 */
public class ReportBuilder {

    private FeedbackMessage message = null;
    private PlatformData userPlatform = null;
    private Map<UserContactMethod, Object> userContactOptions = null;
    private List<ReportMethod> reportMethods = null;

    private FeedbackReport buildFeedbackReport(UserFeedback feedbackForm) {
        // setUserPlatform();
        setReportMethods();
        setUserContactMethods();
        setUserMessage();

        FeedbackReport report = new FeedbackReport(this.message, this.reportMethods, this.userContactOptions,
                this.userPlatform);
        return null;
    }

    /**
     * Create a {@code FeedbackReport} from a {@code UserFeedback} instance and an {@code HttpServletrequest}.
     * 
     * @param feedbackForm
     * @param request
     * @return
     */
    public FeedbackReport buildFeedbackReport(UserFeedback feedbackForm, HttpServletRequest request) {

        setUserPlatform(mapUserPlatform(request));
        setReportMethods();
        setUserContactMethods();
        setUserMessage();

        FeedbackReport report = new FeedbackReport(this.message, this.reportMethods, this.userContactOptions,
                this.userPlatform);
        return report;
    }

    private void setUserMessage() {
        FeedbackMessage message = new FeedbackMessage();
        message.setDescription("Hello reporting system!");
        this.message = message;

    }

    /**
     * Map header data in an HTTP request to a corresponding {@code PlatformData} object.
     * 
     * @param request
     *            Http request.
     * @return .
     */
    public static PlatformData mapUserPlatform(HttpServletRequest request) {
        PlatformData platform = new PlatformData();
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        String referer = request.getHeader(REFERER_HEADER);
        Date timeStamp = new Date(); // Capture current time, not User's time.

        platform.setBrowser(UserAgentUtils.getBrowser(userAgent));
        platform.setOperatingSystem(UserAgentUtils.getOS(userAgent));
        platform.setTimeStamp(timeStamp);
        platform.setReferer(referer);
        platform.setIpAddress(request.getRemoteAddr());
        platform.setUserId(request.getRemoteUser());

        return platform;
    }

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String REFERER_HEADER = "Referer";

    private void setUserPlatform(PlatformData platform) {
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
