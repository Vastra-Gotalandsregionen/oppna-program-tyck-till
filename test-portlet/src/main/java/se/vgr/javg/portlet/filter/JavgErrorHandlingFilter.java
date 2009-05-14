package se.vgr.javg.portlet.filter;

import java.io.IOException;
import java.net.URLEncoder;

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
 * TODO When ready - move this to referense architecture - javg! 
 * @author sofiajonsson
 *
 */
public class JavgErrorHandlingFilter implements RenderFilter, ActionFilter {

	private String tyckTillErrorFormURL;
	public void doFilter(RenderRequest arg0, RenderResponse arg1,
			FilterChain arg2) throws IOException, PortletException {
		if (arg0.getParameter("errorInActionPhase") != null){
			arg1.getWriter().write(createTyckTillPopupLink(arg0.getParameter("errorInActionPhase")));
		}
		else{
			try{
				arg2.doFilter(arg0, arg1);
			}
			catch(Exception e){
				String errorFormURL = tyckTillErrorFormURL+"?errorMessage=" + URLEncoder.encode(e.getMessage(), "UTF-8");
				arg1.getWriter().write(createTyckTillPopupLink(errorFormURL));

			}
		}

	}

	private String createTyckTillPopupLink(String errorFormURL) {
		StringBuffer buf = new StringBuffer();
		buf.append("Ett ov&#228;ntat fel har uppst&#229;tt, ");
		buf.append("<a href=\"#\" onClick=\"window.open('");
		buf.append(errorFormURL);
		buf.append("', 'tyck-till-window', 'menubar=no,width=670,height=450,toolbar=no')");
		buf.append("\">klicka h&#257;r</a> f&#246;r att hj&#257;lpa portalen att bli b&#257;ttre genom att skicka en felrapport.");
		return buf.toString();
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws PortletException {
		tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");
	}

	public void doFilter(ActionRequest arg0, ActionResponse arg1,
			FilterChain arg2) throws IOException, PortletException {
		try{
			arg2.doFilter(arg0, arg1);
		}
		catch(Exception e){
			arg1.setRenderParameter("errorInActionPhase", tyckTillErrorFormURL+"?errorMessage=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
		}

	}
	


}
