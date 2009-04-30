package se.vgr.incidentreport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.misc.BASE64Decoder;


@Consumes("application/xml")
@Provider
public class IncidentReportReader implements MessageBodyReader<IncidentReport> {

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	public IncidentReport readFrom(Class<IncidentReport> c, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> mvm, InputStream is)
			throws IOException, WebApplicationException {
		
		System.out.println("in ir reader");
		try {
			
			/*
			 *  <incidentReport xmlns="">
			                	<description><%=(request.getParameter("errorMessage") == null)? "": request.getParameter("errorMessage") %></description>
			                	<screenShot>
			                		<sendScreenShot>true</sendScreenShot>
			                		<files>
					                    <file filename="" mediatype=""/>
					                </files>
			                	</screenShot>
			                	
			                </incidentReport>
			 */
			IncidentReport ir = new IncidentReport();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			
			ir.setReportType(parseString(doc, "reportType"));
			ir.setErrorTypes(parseString(doc,"errorType").split(" "));
			ir.setDescription(parseString(doc, "description"));
			
			ir.setSendFeedback(parseBoolean(doc, "sendFeedback"));
			if (ir.isSendFeedback()){
				Element feedbackByMail = getElm(doc,"feedbackByMail");
				ir.setFeedbackByMail(feedbackByMail.getAttribute("activated").equals("true"));
				if (ir.isFeedbackByMail()){
					ir.setEmailAddress(parseString(feedbackByMail, "email"));
				}
				
				Element feedbackBySms = getElm(doc,"feedbackBySms");
				ir.setFeedbackBySms(feedbackBySms.getAttribute("activated").equals("true"));
				if (ir.isFeedbackBySms()){
					ir.setSmsPhoneNumber(parseString(feedbackBySms, "phoneNumber"));
				}
				
				Element feedbackByPhone = getElm(doc,"feedbackByPhone");
				ir.setFeedbackByPhone(feedbackByPhone.getAttribute("activated").equals("true"));
				if (ir.isFeedbackByPhone()){
					ir.setPhoneNumber(parseString(feedbackByPhone, "phoneNumber"));
				}
			}
			
			ir.setSendScreenShot(parseBoolean(doc, "sendScreenShot"));
			if (ir.isSendScreenShot()){
				NodeList files = doc.getElementsByTagName("file");
				for (int i = 0; i < files.getLength(); i++){
					Node fileNode = files.item(i);
					BASE64Decoder decoder = new BASE64Decoder();
					if (fileNode.getFirstChild() != null){
						byte[] fileBytes = decoder.decodeBuffer(fileNode.getFirstChild().getTextContent());
					}
					//File file = new File()
				}
			}
			
			return ir;
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}

	private String parseString(Element feedbackByMail, String elmName) {
		if (feedbackByMail.getElementsByTagName(elmName).getLength() == 0)
			return null;
		Element elm = (Element)feedbackByMail.getElementsByTagName(elmName).item(0);
		return elm.getFirstChild().getTextContent();
		
	}

	private String parseString(Document doc, String elmName) {
		if (doc.getElementsByTagName(elmName).getLength() == 0)
			return "";
		Element elm = (Element)doc.getElementsByTagName(elmName).item(0);
	
		return (elm.getFirstChild() == null) ? "":elm.getFirstChild().getTextContent();
	}
	
	private Element getElm(Document doc, String elmName) {
		if (doc.getElementsByTagName(elmName).getLength() == 0)
			return null;
		return (Element)doc.getElementsByTagName(elmName).item(0);
	
	}
	
	private boolean parseBoolean(Document doc, String elmName) {
		if (doc.getElementsByTagName(elmName).getLength() == 0)
			return false;
		Element elm = (Element)doc.getElementsByTagName(elmName).item(0);
		return (elm.getFirstChild() == null) ? false:elm.getFirstChild().getTextContent().equals("true");
	}

	public boolean isReadable(Class<?> cls, Type type, Annotation[] annotations,
			MediaType mediaType) {
		return IncidentReport.class.isAssignableFrom(cls);
	}

}
