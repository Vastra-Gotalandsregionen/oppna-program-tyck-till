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

package se.vgr.tycktill.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;

/**
 * Portlet for testing the Tyck till system.
 */
public class TyckTillTestPortlet extends GenericPortlet {
    private final static Logger log = Logger.getLogger(TyckTillTestPortlet.class);

    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        //System.out.println("in TyckTillTestPortlet doView2");
        log.info("Logging in TyckTillTestPortlet doView");
        if (request.getParameter("throwExceptionInView") != null) {
            throw new RuntimeException("An error occured in doView");
        }
        getPortletContext().getRequestDispatcher("/jsp/test.jsp").include(request, response);
        // System.out.println("dispatcher=" + dispatcher);
    }

    @Override
    public void processAction(ActionRequest arg0, ActionResponse arg1) throws PortletException, IOException {
        if (arg0.getParameter("throwExceptionInAction") != null) {
            throw new RuntimeException("An error occured in processAction");
        }
    }

    @Override
    public void processEvent(EventRequest arg0, EventResponse arg1) throws PortletException, IOException {
        System.out.println("in processEvent");
    }

    @Override
    public void serveResource(ResourceRequest arg0, ResourceResponse arg1) throws PortletException, IOException {
        System.out.println("in serveResource");
    }
}
