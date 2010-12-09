package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import se.vgregion.userfeedback.FeedbackReport.ReportMethod;
import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.userfeedback.domain.UserContact;
import se.vgregion.userfeedback.domain.UserFeedback;

/**
 * This is a utility class for creating a {@code FeedbackReport} from the data in a {@code UserFeedback} instance.
 * 
 * @author Arakun
 * 
 */
public class ReportBuilder {

    @Autowired
    private PlatformDataService platformDataService;

    public PlatformDataService getPlatformDataService() {
        return platformDataService;
    }

    public void setPlatformDataService(PlatformDataService platformDataService) {
        this.platformDataService = platformDataService;
    }

    private FeedbackMessage message = null;
    private PlatformData userPlatform = null;
    private List<UserContact> userContactOptions = null;
    private List<ReportMethod> reportMethods = null;

    /**
     * Create a {@code FeedbackReport} from a {@code UserFeedback} instance and an {@code HttpServletrequest}.
     * 
     * @param feedbackForm
     * @param request
     * @return
     */
    public FeedbackReport buildFeedbackReport(UserFeedback feedbackForm, HttpServletRequest request) {

        setUserMessage(feedbackForm);
        setUserPlatform(platformDataService.mapUserPlatform(request));
        setReportMethods(feedbackForm);
        setUserContactMethods(feedbackForm);

        FeedbackReport report = new FeedbackReport(this.message, this.reportMethods, this.userContactOptions,
                this.userPlatform);
        return report;
    }

    private void setUserMessage(UserFeedback feedbackForm) {
        FeedbackMessage newMessage = new FeedbackMessage();
        newMessage.setDescription(feedbackForm.getCaseMessage());
        StringBuilder sb = new StringBuilder();
        sb.append(feedbackForm.getCaseCategory());
        List<String> subCats = feedbackForm.getCaseSubCategories();
        for (String subCat : subCats) {
            sb.append(" - " + subCat);
        }
        newMessage.setReportType(sb.toString());
        this.message = newMessage;
    }

    private void setUserPlatform(PlatformData platform) {
        this.userPlatform = platform;
    }

    /**
     * Inspect form data and determine which method(s) should be used to contact the customer.
     * 
     * @param feedbackForm
     */
    private void setUserContactMethods(UserFeedback feedbackForm) {

        List<UserContact> contactOptions = new ArrayList<UserContact>();
        UserContact contact = feedbackForm.getUserContact();

        // Change this to a loop if several contact options should be chosen
        contactOptions.add(contact);
        this.userContactOptions = contactOptions;
    }

    /**
     * Inspect form object and determine which report method(s) should be used.
     * 
     * @param feedbackForm
     */
    private void setReportMethods(UserFeedback feedbackForm) {
        List<ReportMethod> methods = new ArrayList<FeedbackReport.ReportMethod>();
        Backend backend = feedbackForm.getCaseBackend();

        assert (message != null) : "setMessage() should be called before serReportMethods()";
        if (backend.getPivotal() != null) {
            methods.add(FeedbackReport.ReportMethod.pivotal);
            message.setTrackerCategory(backend.getPivotal());
        }
        if (backend.getUsd() != null) {
            methods.add(FeedbackReport.ReportMethod.usd);
            message.setTrackerCategory(backend.getUsd());
        }
        if (backend.getMbox() != null) {
            methods.add(FeedbackReport.ReportMethod.email);
            message.setReportEmail(backend.getMbox());
        }
        this.reportMethods = methods;
    }
}
