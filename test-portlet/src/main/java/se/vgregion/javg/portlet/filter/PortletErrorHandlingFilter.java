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

package se.vgregion.javg.portlet.filter;

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

import se.vgregion.ldapservice.LdapService;
import se.vgregion.ldapservice.LdapUser;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(PortletErrorHandlingFilter.class);

    @Override
    public void doFilter(RenderRequest request, RenderResponse arg1, FilterChain arg2) throws IOException,
            PortletException {
        String nameSpace = arg1.getNamespace();

        if (request.getParameter("errorInActionPhase") != null) {
            // if the portlet threw an exception during the action phase we
            // won't go into the view phase
            Map<String, ?> userInfo = (Map<String, ?>) request.getAttribute(PortletRequest.USER_INFO);
            String userId = "";

            if (userInfo != null) {
                userId = (String) userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID.toString());
            }

            arg1.getWriter().write(
                    createTyckTillPopupLink(request.getParameter("errorInActionPhase"), userId, nameSpace));
        } else {
            try {
                // call next in line (either another filter or the portlet
                // itself)
                arg2.doFilter(request, arg1);
            } catch (Throwable e) {
                LOGGER.info("Exception caught in PortletErrorHandlingFilter", e);
                Map<String, ?> userInfo = (Map<String, ?>) request.getAttribute(PortletRequest.USER_INFO);
                String userId = "";
                if (userInfo != null) {
                    userId = (String) userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID.toString());
                }

                try {
                    arg1.getWriter().write(createTyckTillPopupLink(e.toString(), userId, nameSpace));
                } catch (Throwable t) {
                    LOGGER.error("Failed to create link", t);

                }
            }
        }
    }

    private String createTyckTillPopupLink(String errorMessage, String userId, String nameSpace)
            throws UnsupportedEncodingException {
        String email = "";
        String phoneNumber = "";

        if (userId != null && "" != userId) {
            LdapUser ldapUser = getLdapService().getLdapUserByUid(userId);
            if (ldapUser != null) {
                email = ldapUser.getAttributeValue("mail");
                phoneNumber = ldapUser.getAttributeValue("telephoneNumber");
            }

        } else {
            userId = "anonymous";
        }

        StringBuffer errorFormUrl = new StringBuffer(tyckTillErrorFormURL);
        errorFormUrl.append("?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
        errorFormUrl.append("&errorType=" + URLEncoder.encode("errorMessage", "UTF-8"));

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
        buf.append("<script language=\"javascript\">\n");
        buf.append("function openPopup(url) {\n");
        buf.append("newwindow=window.open(url,'name','menubar=no,width=670,height=450,toolbar=no');\n");
        buf.append("if (window.focus) {newwindow.focus()}\n");
        buf.append("return false;\n");
        buf.append("}\n");
        buf.append("// -->\n");
        buf.append("</script>\n");
        buf.append("Ett ov&#228;ntat fel har uppst&#229;tt, ");
        // Javascript enabled implies that onClick will run
        // buf.append("<script language=\"javascript\">\n");
        buf.append("<a href='#pop' " + "onClick=\"javascript:openPopup('" + errorFormUrl
                + "&javasscript=yes')\" >");
        buf.append("klicka h&#257;r</a> f&#246;r att hj&#257;lpa "
                + "portalen att bli b&#257;ttre genom att skicka en felrapport.");
        // buf.append("// -->\n");
        // buf.append("</script>\n");

        buf.append("<noscript>\n");
        buf.append("<a href=\"" + errorFormUrl + "\" >");
        buf.append("klicka h&#257;r</a> f&#246;r att hj&#257;lpa "
                + "portalen att bli b&#257;ttre genom att skicka en felrapport.");
        buf.append("</noscript>\n");
        return buf.toString();
    }

    private LdapService getLdapService() {
        return (LdapService) ac.getBean("ldapService");
    }

    @Override
    public void init(FilterConfig arg0) throws PortletException {
        tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");
        ac = PortletApplicationContextUtils.getWebApplicationContext(arg0.getPortletContext());

        contextName = arg0.getInitParameter("ApplicationName");
        reportMethod = arg0.getInitParameter("TyckTillReportMethod");
        reportEmail = arg0.getInitParameter("TyckTillReportEmail");
    }

    @Override
    public void doFilter(ActionRequest arg0, ActionResponse arg1, FilterChain arg2) throws IOException,
            PortletException {
        try {
            // call next in line (either another filter or the portlet itself)
            arg2.doFilter(arg0, arg1);
        } catch (Throwable e) {
            // Save the error for the view phase, where we can take control over
            // the response-rendering
            arg1.setRenderParameter("errorInActionPhase", (e.toString()));
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
}