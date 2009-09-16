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

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Consumes("application/xml")
@Provider
public class IncidentReportReader implements MessageBodyReader<IncidentReport> {

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
            System.out.println(doc.getTextContent());
        }
        catch (Exception e) {
            throw new RuntimeException("Error creating xml", e);
        }

        ir.setReportType(parseString(doc, "reportType"));
        String[] errorTypes = parseString(doc, "errorType").split(" ");
        for (String errorType : errorTypes) {
            ir.addErrorType(errorType);
        }

        ir.setDescription(parseString(doc, "description"));
        ir.setDefaultErrorMessage(parseString(doc, "defaultErrorMessage"));
        ir.setBrowser(parseString(doc, "browser"));
        ir.setTimeStamp(parseString(doc, "timestamp"));
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
                    File file = new File(fileNode.getFirstChild().getTextContent().replaceFirst("file:", ""));
                    ir.addScreenShot(file);
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
