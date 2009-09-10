/**
 * Copyright 2009 Västra Götalandsregionen
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
package se.vgr.incidentreport.impl;

import org.springframework.stereotype.Service;

import se.vgr.incidentreport.IncidentReport;
import se.vgr.incidentreport.IncidentReportService;

@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

	public IncidentReport reportIncident(IncidentReport ir) {
		// TODO Call USD and report incident.
		// TODO Decide what the method should return? Will USD return a status code 
		// or something similar that we should return?
		
		System.out.println("=====TEST BEGIN=====");
		System.out.println(ir.getEmailAddress());
		System.out.println(ir.getPhoneNumber());
		System.out.println("======TEST END======");
		
		return null;
	}

}
