package se.vgr.javg.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
public class WebErrorHandlingFilter implements Filter {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    private String tyckTillErrorFormURL;
    private ApplicationContext ac;
    private Object contextName;
    private String reportMethod;
    private String reportEmail;

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
            ServletException {

        try {
            // call next in line (either another filter or the portlet
            // itself)
            arg2.doFilter(arg0, arg1);
        }
        catch (Exception e) {
            arg1.getWriter().write(
                    createTyckTillPopupLink(e.toString(), ((HttpServletRequest) arg0).getRemoteUser()));
        }
    }

    private String createTyckTillPopupLink(String errorMessage, String userId) throws UnsupportedEncodingException {

        String email = "";
        String phoneNumber = "";

        userId = "andcu1";

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

    public void init(FilterConfig arg0) throws ServletException {
        tyckTillErrorFormURL = arg0.getInitParameter("TyckTillErrorFormURL");
        String ldapContextConfigLocation = arg0.getServletContext().getInitParameter("ldapContextConfigLocation");
        ac = new FileSystemXmlApplicationContext(ldapContextConfigLocation);
        contextName = arg0.getServletContext().getServletContextName();
        reportMethod = arg0.getInitParameter("TyckTillReportMethod");
        reportEmail = arg0.getInitParameter("TyckTillReportEmail");
        getLdapService(); // Test Before use
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}
