package se.vgregion.userfeedback.impl;

import java.util.HashMap;
import java.util.Map;

import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.incidentreport.pivotaltracker.PivotalTrackerService;
import se.vgregion.util.Attachment;

public class PivotalServiceMock implements PivotalTrackerService {

    private static String DEFAULT_STORY_ID = "My_story_id";
    private PTStory userStory;
    private Map<String, Attachment> attachments = new HashMap<String, Attachment>();

    @Override
    public void addAttachmentToStory(String arg0, PTStory arg1) {
        for (Attachment attachment : arg1.getAttachments()) {
            attachments.put(attachment.getFilename(), attachment);
        }
    }

    @Override
    public String createuserStory(PTStory arg0) {
        userStory = arg0;
        return DEFAULT_STORY_ID;
    }

    public PTStory getUserStory() {
        return userStory;
    }

    public Map<String, Attachment> getAttachments() {
        return attachments;
    }
}
