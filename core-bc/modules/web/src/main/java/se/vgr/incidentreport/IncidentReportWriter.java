package se.vgr.incidentreport;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Produces("application/xml")
@Provider
public class IncidentReportWriter implements MessageBodyWriter<IncidentReport> {

	public long getSize(IncidentReport c, Class<?> arg1, Type arg2,
	Annotation[] arg3, MediaType arg4) {
		return -1;
	}

	
	public void writeTo(IncidentReport c, Class<?> cls, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> mvm, OutputStream os)
			throws IOException, WebApplicationException {
		StringBuilder sb = new StringBuilder();
		sb.append("<incidentReport xmlns=\"\">");
		sb.append("<reportType>").append(c.getReportType()).append("</reportType>");
		sb.append("<errorType>");
		for (String errorType : c.getErrorTypes()) {
			sb.append(errorType + " ");
		}
		sb.append("</errorType>");
		sb.append("<timeStamp/>"); //TODO add this.
		
		sb.append("<defaultErrorMessage/>"); //TODO add this.
		sb.append("<description>").append(c.getDescription()).append("</description>");
		
		sb.append("<feedback>");
		sb.append("<sendFeedback>").append(c.isSendFeedback()).append("</sendFeedback>");
		
		sb.append("<feedbackBySms activated=\"").append(c.isFeedbackBySms()).append("\">");
		sb.append("<phoneNumber>").append(c.getSmsPhoneNumber()).append("</phoneNumber>");
		sb.append("</feedbackBySms>");
		
		sb.append("<feedbackByMail activated=\"").append(c.isFeedbackByMail()).append("\">");
		sb.append("<email>").append(c.getEmailAddress()).append("</email>");
		sb.append("</feedbackByMail>");
		
		sb.append("<feedbackByPhone activated=\"").append(c.isFeedbackByPhone()).append("\">");
		sb.append("<phoneNumber>").append(c.getPhoneNumber()).append("</phoneNumber>");
		sb.append("</feedbackByPhone>");
		
		sb.append("</feedback>");
		
		sb.append("<screenShot>");
		sb.append("<sendScreenShot>").append(c.isSendScreenShot()).append("</sendScreenShot>");
		sb.append("<files>");
		for (File file : c.getScreenShots()) {
			sb.append("<file filename=\"").append(file.getName()).append("\" mediatype=\"\" />"); //TODO get mediatype
		}
		sb.append("</files>");
		
		sb.append("</screenShot>");
		sb.append("</incidentReport>");
		
		
		os.write(sb.toString().getBytes("UTF-8"));
		os.flush();
	}

	

	public boolean isWriteable(Class<?> cls, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		return IncidentReport.class.isAssignableFrom(cls);
	}

}
