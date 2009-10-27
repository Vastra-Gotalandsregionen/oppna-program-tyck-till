package se.vgr.incidentreport.pivotaltracker;

import java.util.Properties;

import se.vgr.incidentreport.IncidentReport;

public interface PivotalTrackerService {

    /**
     * @param ir
     * @return the url of the story in PT
     */
    String createuserStory(IncidentReport ir);

    Properties getPivotalTrackerMappings();

}
