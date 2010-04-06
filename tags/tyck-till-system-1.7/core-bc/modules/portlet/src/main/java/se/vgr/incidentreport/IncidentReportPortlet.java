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

package se.vgr.incidentreport;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.portlet.context.PortletApplicationContextUtils;

import se.vgr.ldapservice.LdapService;
import se.vgr.ldapservice.LdapUser;

/**
 * Gathers information about the user and presents the user input form.
 */
public class IncidentReportPortlet extends GenericPortlet {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private ApplicationContext ac;
    private String contextName;
    private String reportMethod;
    private String reportEmail;
    private String tyckTillErrorFormURL;

    @Override
    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        tyckTillErrorFormURL = config.getInitParameter("TyckTillErrorFormURL");
        ac = PortletApplicationContextUtils.getWebApplicationContext(config.getPortletContext());

        contextName = config.getInitParameter("ApplicationName");
        reportMethod = config.getInitParameter("TyckTillReportMethod");
        reportEmail = config.getInitParameter("TyckTillReportEmail");

        getLdapService(); // Test connection Before use
    }

    @Override
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        Map<String, ?> userInfo = (Map<String, ?>) request.getAttribute(PortletRequest.USER_INFO);

        // System.out.println("UserInfo:"+userInfo);
        String userId = (String) (userInfo != null ? userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID
                .toString()) : "");
        String email = "";
        String phoneNumber = "";

        if (userId != null && "" != userId) {
            LdapUser ldapUser = getLdapService().getLdapUserByUid(userId);
            // System.out.println("Ldap values:" + ldapUser.getAttributes());
            if (ldapUser != null) {
                email = ldapUser.getAttributeValue("mail");
                phoneNumber = ldapUser.getAttributeValue("telephoneNumber");
                // if (phoneNumber == null || phoneNumber.length() < 5) {
                // phoneNumber = ldapUser.getAttributeValue("hsaSedfSwitchboardTelephoneNo");
                // }
                // System.out.println("email=" + email + " phoneNumber=" + phoneNumber);
            }

        } else {
            userId = "anonymous";
        }

        StringBuffer errorFormParams = new StringBuffer();

        errorFormParams.append("timestamp=" + URLEncoder.encode(df.format(new Date()), "UTF-8"));
        if (email != null && "" != email) {
            errorFormParams.append("&email=" + URLEncoder.encode(email, "UTF-8"));
        }
        if (phoneNumber != null && "" != phoneNumber) {
            errorFormParams.append("&phoneNumber=" + URLEncoder.encode(phoneNumber, "UTF-8"));
        }

        errorFormParams.append("&context=" + URLEncoder.encode(contextName, "UTF-8"));
        errorFormParams.append("&reportMethod=" + URLEncoder.encode(reportMethod, "UTF-8"));
        errorFormParams.append("&reportEmail=" + URLEncoder.encode(reportEmail, "UTF-8"));
        errorFormParams.append("&userid=" + userId);
        request.setAttribute("TyckTillErrorFormURL", URLEncoder.encode(tyckTillErrorFormURL, "UTF-8"));
        request.setAttribute("errorFormParams", errorFormParams.toString());

        response.setContentType("text/html");
        PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");

        dispatcher.include(request, response);
    }

    private LdapService getLdapService() {
        return (LdapService) ac.getBean("ldapService");
    }

    @Override
    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
    }
}
