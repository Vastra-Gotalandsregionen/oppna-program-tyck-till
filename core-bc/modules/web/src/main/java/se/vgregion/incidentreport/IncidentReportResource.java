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
 *
 */

package se.vgregion.incidentreport;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import se.vgregion.incidentreport.IncidentReport;
import se.vgregion.incidentreport.IncidentReportService;

import com.sun.jersey.api.spring.Autowire;
import com.sun.jersey.spi.inject.Inject;

/**
 * Web service that receives an incident report object and calls the report service. 
 */
@Autowire
@Path("incidentReport")
public class IncidentReportResource {

    @Inject
    private IncidentReportService incidentReportService;

    @POST
    @Produces("application/xml")
    public void post(@Context UriInfo uriInfo, IncidentReport ir) {
        // System.out.println("In IncidentReportResource.post ir=" + ir);
        incidentReportService.reportIncident(ir);
    }
}
