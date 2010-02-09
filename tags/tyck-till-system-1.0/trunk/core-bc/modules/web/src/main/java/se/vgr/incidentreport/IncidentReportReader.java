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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.vgr.util.UserAgentUtils;

@Consumes("application/xml")
@Provider
public class IncidentReportReader implements MessageBodyReader<IncidentReport> {

    private static final Logger logger = LoggerFactory.getLogger(IncidentReportReader.class);
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    private Locale svLocale = new Locale("sv", "SE");
    private ResourceBundle rb = ResourceBundle.getBundle("messages", svLocale);

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public IncidentReport readFrom(Class<IncidentReport> c, Type genericType, Annotation[] annotations,
            javax.ws.rs.core.MediaType arg3, MultivaluedMap<String, String> arg4, InputStream is)
            throws IOException, WebApplicationException {
        try {
            return parseIncidentReport(is);
        }
        catch (Exception e) {
            throw new WebApplicationException(e);
        }

    }

    protected IncidentReport parseIncidentReport(InputStream is) {

        IncidentReport ir = new IncidentReport();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            // System.out.println(doc.getTextContent());
        }
        catch (Exception e) {
            throw new RuntimeException("Error creating xml", e);
        }
        String reportType = parseString(doc, "reportType");
        String reportType2 = reportType;
        try {
            reportType2 = rb.getString("incidentreport.reportTypes." + reportType + ".label");
        }
        catch (Exception e) {
            // ignore
        }
        ir.setReportType(reportType2);
        String[] errorTypes = parseString(doc, "errorType").split(" ");
        for (String errorType : errorTypes) {
            String errorType2 = errorType;
            try {
                errorType2 = rb.getString("incidentreport.errorTypes." + errorType + ".label");
            }
            catch (Exception e) {
                // ignore
            }

            ir.addErrorType(errorType2);
        }

        ir.setDescription(parseString(doc, "description"));
        ir.setDefaultErrorMessage(parseString(doc, "defaultErrorMessage"));
        ir.setBrowser(UserAgentUtils.getBrowser(parseString(doc, "browser")));
        ir.setOs(UserAgentUtils.getOS(parseString(doc, "browser")));
        ir.setJavascript(parseString(doc, "javascript-enabled"));
        String timestamp = parseString(doc, "timestamp");
        if (timestamp == null || "".equals(timestamp)) {
            timestamp = df.format(new Date());
        }
        ir.setTimeStamp(timestamp);
        ir.setIpAddress(parseString(doc, "ip-address"));
        ir.setReferer(parseString(doc, "referer"));
        ir.setApplicationName(parseString(doc, "application-name"));
        ir.setNameSpace(parseString(doc, "name-space"));
        ir.setReportMethod(parseString(doc, "report-method"));
        ir.setReportEmail(parseString(doc, "report-email"));
        ir.setSendFeedback(parseBoolean(doc, "sendFeedback"));
        ir.setUserId(parseString(doc, "userid"));
        if (ir.isSendFeedback()) {
            Element feedbackByMail = getElm(doc, "feedbackByMail");
            ir.setFeedbackByMail(feedbackByMail.getAttribute("activated").equals("true"));
            if (ir.isFeedbackByMail()) {
                ir.setEmailAddress(parseString(feedbackByMail, "email"));
            }

            Element feedbackBySms = getElm(doc, "feedbackBySms");
            ir.setFeedbackBySms(feedbackBySms.getAttribute("activated").equals("true"));
            if (ir.isFeedbackBySms()) {
                ir.setSmsPhoneNumber(parseString(feedbackBySms, "phoneNumber"));
            }

            Element feedbackByPhone = getElm(doc, "feedbackByPhone");
            ir.setFeedbackByPhone(feedbackByPhone.getAttribute("activated").equals("true"));
            if (ir.isFeedbackByPhone()) {
                ir.setPhoneNumber(parseString(feedbackByPhone, "phoneNumber"));
            }
        }

        ir.setSendScreenShot(parseBoolean(doc, "sendScreenShot"));
        if (ir.isSendScreenShot()) {
            NodeList files = doc.getElementsByTagName("file");
            for (int i = 0; i < files.getLength(); i++) {
                Node fileNode = files.item(i);
                if (fileNode != null && fileNode.getFirstChild() != null) {
                    String path = fileNode.getFirstChild().getTextContent().trim();
                    URL url;
                    File file = null;
                    try {
                        url = new URL(path);
                        file = new File(url.toURI());
                    }
                    catch (Exception e1) {
                        logger.warn("Filename could not be read.", e1);
                        continue;
                    }

                    Screenshot ss = new Screenshot();
                    String fileName = "noname";
                    try {
                        fileName = fileNode.getAttributes().getNamedItem("filename").getTextContent();

                    }
                    catch (Exception e) {
                        logger.warn("Filename could not be read.", e);
                        continue;
                    }
                    ss.setFileName(fileName);
                    ss.setPath(file.getAbsolutePath());
                    ir.addScreenShot(ss);

                }
            }
        }

        return ir;
    }

    private String parseString(Element parent, String elmName) {
        if (parent.getElementsByTagName(elmName).getLength() == 0) {
            return null;
        }
        Element elm = (Element) parent.getElementsByTagName(elmName).item(0);
        return (elm.getFirstChild() == null ? null : elm.getFirstChild().getTextContent());

    }

    private String parseString(Document doc, String elmName) {
        if (doc.getElementsByTagName(elmName).getLength() == 0) {
            return "";
        }
        Element elm = (Element) doc.getElementsByTagName(elmName).item(0);

        return (elm.getFirstChild() == null) ? "" : elm.getFirstChild().getTextContent();
    }

    private Element getElm(Document doc, String elmName) {
        if (doc.getElementsByTagName(elmName).getLength() == 0) {
            return null;
        }
        return (Element) doc.getElementsByTagName(elmName).item(0);

    }

    private boolean parseBoolean(Document doc, String elmName) {
        if (doc.getElementsByTagName(elmName).getLength() == 0) {
            return false;
        }
        Element elm = (Element) doc.getElementsByTagName(elmName).item(0);
        return (elm.getFirstChild() == null) ? false : elm.getFirstChild().getTextContent().equals("true");
    }

    public boolean isReadable(Class<?> cls, Type arg1, Annotation[] arg2, javax.ws.rs.core.MediaType arg3) {
        return IncidentReport.class.isAssignableFrom(cls);
    }

    

}
