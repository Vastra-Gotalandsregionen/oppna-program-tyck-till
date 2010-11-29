package se.vgregion.userfeedback;

import se.vgregion.userfeedback.domain.UserFeedback;

/**
 * This is a utility class for creating a {@code FeedbackReport} from the data in a {@code UserFeedback} instance.
 * 
 * @author Arakun
 * 
 */
public class FeedbackReportBuilder {

    public static FeedbackReport buildFeedbackReport(UserFeedback feedback) {
        return null;
    }

    private void fillInMessagePart() {
        FeedbackMessage message = new FeedbackMessage();
        message.setDescription("Hello reporting system!");

    }

    private void setUserContactOptions() {

    }

    private void setReportMethods() {

    }
}
