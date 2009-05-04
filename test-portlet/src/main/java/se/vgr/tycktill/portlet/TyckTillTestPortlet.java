package se.vgr.tycktill.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

public class TyckTillTestPortlet extends GenericPortlet{

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		
		if (request.getParameter("throwExceptionInView") != null)
			throw new RuntimeException("An error occured in doView");
		PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/jsp/test.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void processAction(ActionRequest arg0, ActionResponse arg1)
			throws PortletException, IOException {
		if (arg0.getParameter("throwExceptionInAction") != null)
			throw new RuntimeException("An error occured in processAction");
	}

	@Override
	public void processEvent(EventRequest arg0, EventResponse arg1)
			throws PortletException, IOException {
		System.out.println("in processEvent");
	}


	@Override
	public void serveResource(ResourceRequest arg0, ResourceResponse arg1)
			throws PortletException, IOException {
		System.out.println("in serveResource");
	}

	
}
