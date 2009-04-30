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
package se.vgr.incidentreport;

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
    public IncidentReport get() {
    	System.out.println("In getIncidentReport");
    	
     	return new IncidentReport();
    }
    
    @POST 
    @Produces("application/xml")
    public IncidentReport post(@Context UriInfo uriInfo, IncidentReport ir) {
    	System.out.println("In postIncidentReport [" + ir.description + "]"); 
    	return ir;
    }

}

