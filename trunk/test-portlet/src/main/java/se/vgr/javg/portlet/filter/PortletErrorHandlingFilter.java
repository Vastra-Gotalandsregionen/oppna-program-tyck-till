package se.vgr.javg.portlet.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.portlet.context.PortletApplicationContextUtils;

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

    private static final Logger logger = LoggerFactory.getLogger(PortletErrorHandlingFilter.class);

    public void doFilter(RenderRequest request, RenderResponse arg1, FilterChain arg2) throws IOException,
            PortletException {
        System.out.println("in PortletErrorHandlingFilter doFilter");

        String nameSpace = arg1.getNamespace();

        Map<String, ?> userInfo = (Map<String, ?>) request.getAttribute(PortletRequest.USER_INFO);
        String userId = (String) ((userInfo != null) ? userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID
                .toString()) : "");

        if (request.getParameter("errorInActionPhase") != null) {
            // if the portlet threw an exception during the action phase we
            // won't go into the view phase

            arg1.getWriter().write(
                    createTyckTillPopupLink(request.getParameter("errorInActionPhase"), userId, nameSpace));
        }
        else {
            try {
                // call next in line (either another filter or the portlet
                // itself)
                arg2.doFilter(request, arg1);
            }
            catch (Throwable e) {
                logger.info("Exception caught in PortletErrorHandlingFilter", e);
                try {
                    arg1.getWriter().write(createTyckTillPopupLink(e.toString(), userId, nameSpace));
                }
                catch (Throwable t) {
                    logger.error("Failed to create link", t);

                }
            }
        }

    }

    private String createTyckTillPopupLink(String errorMessage, String userId, String nameSpace)
            throws UnsupportedEncodingException {

        String email = "";
        String phoneNumber = "";

        if (userId != null && "" != userId) {
            LdapUser ldapUser = getLdapService().getLdapUser(null, "(uid=" + userId + ")");
            if (ldapUser != null) {
                email = ldapUser.getAttributeValue("mail");
                phoneNumber = ldapUser.getAttributeValue("telephoneNumber");
            }

        }
        else {
            userId = "anonymous";
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

        errorFormUrl.append("&context=" + URLEncoder.encode(contextName, "UTF-8"));
        errorFormUrl.append("&namespace=" + URLEncoder.encode(nameSpace, "UTF-8"));
        errorFormUrl.append("&reportMethod=" + URLEncoder.encode(reportMethod, "UTF-8"));
        errorFormUrl.append("&reportEmail=" + URLEncoder.encode(reportEmail, "UTF-8"));
        errorFormUrl.append("&userid=" + userId);

        StringBuffer buf = new StringBuffer();
        buf.append("Ett ov&#228;ntat fel har uppst&#229;tt, ");
        // Javascript enabled implies that onClick will run
        buf.append("<a href=\"" + errorFormUrl + "\" " + "onClick=\"this.href='" + errorFormUrl
                + "&javascript=yes'\" \"target=\"_blank\">");
        buf
                .append("klicka h&#257;r</a> f&#246;r att hj&#257;lpa portalen att bli b&#257;ttre genom att skicka en felrapport.");
        return buf.toString();
    }

    private LdapService getLdapService() {
        return (LdapService) ac.getBean("ldapService");

    }

    public void init(FilterConfig arg0) throws PortletException {
        tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");

        // String ldapContextConfigLocation =
        // arg0.getPortletContext().getInitParameter("ldapContextConfigLocation");

        ac = PortletApplicationContextUtils.getWebApplicationContext(arg0.getPortletContext());

        contextName = arg0.getInitParameter("ApplicationName");
        reportMethod = arg0.getInitParameter("TyckTillReportMethod");
        reportEmail = arg0.getInitParameter("TyckTillReportEmail");

        getLdapService(); // Test connection Before use
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
