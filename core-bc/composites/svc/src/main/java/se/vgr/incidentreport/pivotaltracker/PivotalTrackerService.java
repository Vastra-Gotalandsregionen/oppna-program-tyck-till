package se.vgr.incidentreport.pivotaltracker;

import java.util.Properties;

import se.vgr.incidentreport.IncidentReport;

public interface PivotalTrackerService {

    void createuserStory(IncidentReport ir);

    Properties getPivotalTrackerMappings();

}
