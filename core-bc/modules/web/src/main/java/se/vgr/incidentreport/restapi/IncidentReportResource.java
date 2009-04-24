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
package se.vgr.incidentreport.restapi;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("incidentReport")
public class IncidentReportResource {

    @GET 
    @Produces("application/xml")
    public String get() {
    	System.out.println("In getIncidentReport");
    	
     	return getEmptyIncidentReportXml();
    }
    
    @POST 
    @Produces("application/xml")
    public String post(@Context UriInfo uriInfo, String ir) {
    	System.out.println("In postIncidentReport [" + ir + "]"); //TODO parse and do something useful...
    	return getEmptyIncidentReportXml();
    }

	private String getEmptyIncidentReportXml() {
		StringBuffer emptyIncidentReport = new StringBuffer();
    	emptyIncidentReport.append("<incidentReport xmlns=\"\">");
    	emptyIncidentReport.append("<function-category><function/></function-category>");
    	emptyIncidentReport.append("<timeStamp/>");
    	emptyIncidentReport.append("<description>Skriv h&#228;r om du vill f&#246;rklara mer</description>");
    	emptyIncidentReport.append("<feedback>");
    	emptyIncidentReport.append("<sendFeedback/>");
    	emptyIncidentReport.append("<feedbackType/>");
    	emptyIncidentReport.append("</feedback>");
    	emptyIncidentReport.append("<file/>");
    	emptyIncidentReport.append("</incidentReport>");
    	
    	return emptyIncidentReport.toString();
	}

}

