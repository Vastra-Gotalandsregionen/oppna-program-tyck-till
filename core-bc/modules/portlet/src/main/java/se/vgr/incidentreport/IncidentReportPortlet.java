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

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;



public class IncidentReportPortlet extends GenericPortlet {
	
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
	}

	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		// TODO Replace these fake values with the actual logged in user's values (fetch from LDap)
		request.setAttribute("email", "john.doe@domain.com");
		request.setAttribute("phoneNumber", "070-555555");
		response.setContentType("text/html");
		PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(
			"/jsp/index.jsp");
		
		dispatcher.include(request, response);
	}

	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
	
		
	}

}
