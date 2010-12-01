package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.FeedbackReport.UserContactMethod;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserFeedback;

/**
 * This is a utility class for creating a {@code FeedbackReport} from the data in a {@code UserFeedback} instance.
 * 
 * @author Arakun
 * 
 */
public class ReportBuilder {

    @Autowired
    PlatformDataService platformDataService;

    public PlatformDataService getPlatformDataService() {
        return platformDataService;
    }

    public void setPlatformDataService(PlatformDataService platformDataService) {
        this.platformDataService = platformDataService;
    }

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

        setUserPlatform(platformDataService.mapUserPlatform(request));
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
