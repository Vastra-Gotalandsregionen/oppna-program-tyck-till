/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */

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
