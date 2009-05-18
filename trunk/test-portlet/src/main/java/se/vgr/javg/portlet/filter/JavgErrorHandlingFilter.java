package se.vgr.javg.portlet.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
/**
 * TODO When ready - move this to reference architecture/javg. Document usage within the reference architecture, in document Anvisningar_Anslutningsskikt_Portlet.doc. 
 * TODO add support for portlet methods missing, e.g. serveResource
 * TODO Fetch info about the logged in user (from LDAP)
 * TODO Fetch info about the portlet and/or portal page 
 * that caused the error (Note: just url is probably not that interesting since we're
 * within the portal)
 * TODO Create a "cancel"-link/button (that links back to the portal's start page?). 
 * TODO Make the user interface more like the "interaktionsdesign".
 * @author sofiajonsson
 *
 */
public class JavgErrorHandlingFilter implements RenderFilter, ActionFilter {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	private String tyckTillErrorFormURL;
	public void doFilter(RenderRequest arg0, RenderResponse arg1,
			FilterChain arg2) throws IOException, PortletException {
		if (arg0.getParameter("errorInActionPhase") != null){
			// if the portlet threw an exception during the action phase we won't go into the view phase
			arg1.getWriter().write(createTyckTillPopupLink(arg0.getParameter("errorInActionPhase")));
		}
		else{
			try{
				// call next in line (either another filter or the portlet itself)
				arg2.doFilter(arg0, arg1);
			}
			catch(Exception e){
				arg1.getWriter().write(createTyckTillPopupLink(e.toString()));

			}
		}

	}

	private String createTyckTillPopupLink(String errorMessage) throws UnsupportedEncodingException {
		//TODO Fetch actual values from LDAP! 
		String email = "john.doe@domain.com";
		String phoneNumber = "070-555555";
		
		StringBuffer errorFormUrl = new StringBuffer(tyckTillErrorFormURL);
		errorFormUrl.append("?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8")); 
		errorFormUrl.append("&timestamp=" + URLEncoder.encode(df.format(new Date()), "UTF-8"));
		errorFormUrl.append("&email=" + URLEncoder.encode(email, "UTF-8"));
		errorFormUrl.append("&phoneNumber=" + URLEncoder.encode(phoneNumber, "UTF-8"));
		
		
		StringBuffer buf = new StringBuffer();
		buf.append("<script language=\"javascript\">\n");
		buf.append("function openPopup(url) {\n");
		buf.append("newwindow=window.open(url,'name','menubar=no,width=670,height=450,toolbar=no');\n");
		buf.append("if (window.focus) {newwindow.focus()}\n");
		buf.append("return false;\n");
		buf.append("}\n");
		buf.append("// -->\n");
		buf.append("</script>\n");
		
		buf.append("Ett ov&#228;ntat fel har uppst&#229;tt, ");
		buf.append("<a href=\""+errorFormUrl+"\" onClick=\"return openPopup('"+errorFormUrl+"')\">");
		buf.append("klicka h&#257;r</a> f&#246;r att hj&#257;lpa portalen att bli b&#257;ttre genom att skicka en felrapport.");
		return buf.toString();
	}


	public void init(FilterConfig arg0) throws PortletException {
		tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");
	}

	public void doFilter(ActionRequest arg0, ActionResponse arg1,
			FilterChain arg2) throws IOException, PortletException {
		try{
			// call next in line (either another filter or the portlet itself)
			arg2.doFilter(arg0, arg1);
		}
		catch(Exception e){
			//Save the error for the view phase, where we can take control over the response-rendering
			arg1.setRenderParameter("errorInActionPhase", (e.toString()));
		}

	}
	

	public void destroy() {
		// TODO Auto-generated method stub

	}
	


}
