package se.vgr.incidentreport.impl;

import org.springframework.stereotype.Service;

import se.vgr.incidentreport.IncidentReport;
import se.vgr.incidentreport.IncidentReportService;

@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

	public IncidentReport reportIncident(IncidentReport ir) {
		// TODO Call UC and report incident.
		// TODO Decide what the method should return? Will UC return a status code 
		// or something similar that we should return?
		return null;
	}

}
