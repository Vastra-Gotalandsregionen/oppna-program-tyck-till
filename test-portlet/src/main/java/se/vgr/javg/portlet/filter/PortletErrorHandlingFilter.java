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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import se.vgr.ldapservice.LdapService;
import se.vgr.ldapservice.LdapUser;

/**
 * TODO When ready - move this to reference architecture/javg. TODO Document usage within the reference
 * architecture, in document Anvisningar_Anslutningsskikt_Portlet.doc. TODO add support for portlet methods
 * missing, e.g. serveResource TODO Fetch info about the portlet and/or portal page that caused the error TODO
 * Create a "cancel"-link/button (that links back to the portal's start page?). TODO Make the user interface more
 * like the "interaktionsdesign". TODO Add handling in case the LDAP server is unavailable
 * 
 * @author sofiajonsson
 * 
 */
public class PortletErrorHandlingFilter implements RenderFilter, ActionFilter {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private String tyckTillErrorFormURL;
    private ApplicationContext ac;
    private String contextName;
    private String reportEmail;
    private String reportMethod;

    public void doFilter(RenderRequest arg0, RenderResponse arg1, FilterChain arg2) throws IOException,
            PortletException {

        String nameSpace = "";

        if (arg0.getParameter("errorInActionPhase") != null) {
            // if the portlet threw an exception during the action phase we
            // won't go into the view phase
            nameSpace = arg1.getNamespace();
            arg1.getWriter().write(
                    createTyckTillPopupLink(arg0.getParameter("errorInActionPhase"), arg0.getRemoteUser(),
                            nameSpace));
        }
        else {
            try {
                // call next in line (either another filter or the portlet
                // itself)
                arg2.doFilter(arg0, arg1);
            }
            catch (Exception e) {

                arg1.getWriter().write(createTyckTillPopupLink(e.toString(), arg0.getRemoteUser(), nameSpace));

            }
        }

    }

    private String createTyckTillPopupLink(String errorMessage, String userId, String nameSpace)
            throws UnsupportedEncodingException {

        String email = "";
        String phoneNumber = "";

        if (userId != null && "" != userId) {
            LdapUser ldapUser = getLdapService().getLdapUser(null, "(uid=" + userId + ")");
            email = ldapUser.getAttributeValue("mail");
            phoneNumber = ldapUser.getAttributeValue("telephoneNumber");
        }

        StringBuffer errorFormUrl = new StringBuffer(tyckTillErrorFormURL);
        errorFormUrl.append("?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
        errorFormUrl.append("&timestamp=" + URLEncoder.encode(df.format(new Date()), "UTF-8"));
        if (email != null && "" != email) {
            errorFormUrl.append("&email=" + URLEncoder.encode(email, "UTF-8"));
        }
        if (phoneNumber != null && "" != phoneNumber) {
            errorFormUrl.append("&phoneNumber=" + URLEncoder.encode(phoneNumber, "UTF-8"));
        }

        errorFormUrl.append("&context=" + contextName);
        errorFormUrl.append("&namespace=" + nameSpace);
        errorFormUrl.append("&reportMethod=" + reportMethod);
        errorFormUrl.append("&reportEmail=" + reportEmail);
        errorFormUrl.append("&userid=" + userId);

        StringBuffer buf = new StringBuffer();
        buf.append("Ett ov&#228;ntat fel har uppst&#229;tt, ");
        buf.append("<a href=\"" + errorFormUrl + "\" target=\"_blank\">");
        buf
                .append("klicka h&#257;r</a> f&#246;r att hj&#257;lpa portalen att bli b&#257;ttre genom att skicka en felrapport.");
        return buf.toString();
    }

    private LdapService getLdapService() {
        return (LdapService) ac.getBean("ldapService");

    }

    public void init(FilterConfig arg0) throws PortletException {
        tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");
        String ldapContextConfigLocation = arg0.getPortletContext().getInitParameter("ldapContextConfigLocation");
        ac = new FileSystemXmlApplicationContext(ldapContextConfigLocation);
        contextName = arg0.getPortletContext().getPortletContextName();
        reportMethod = arg0.getInitParameter("TyckTillReportMethod");
        reportEmail = arg0.getInitParameter("TyckTillReportEmail");

        getLdapService(); // Test Before use
    }

    public void doFilter(ActionRequest arg0, ActionResponse arg1, FilterChain arg2) throws IOException,
            PortletException {
        try {
            // call next in line (either another filter or the portlet itself)
            arg2.doFilter(arg0, arg1);
        }
        catch (Exception e) {
            // Save the error for the view phase, where we can take control over
            // the response-rendering
            arg1.setRenderParameter("errorInActionPhase", (e.toString()));
        }

    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}
