package se.vgr.incidentreport;

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
import java.util.ArrayList;
import java.util.List;

public class IncidentReport {
    public static final String NEWLINE = "\n";
    String reportType = "";
    List<String> errorTypes = new ArrayList<String>();
    String timeStamp;
    String browser;
    String userId;
    String referer;
    String ipAddress;
    String applicationName;
    String description;
    boolean sendFeedback;
    boolean feedbackBySms;
    String smsPhoneNumber;
    boolean feedbackByMail;
    String emailAddress;
    boolean feedbackByPhone;
    String phoneNumber; // might be different from the sms-phone-number above
    boolean sendScreenShot;
    String defaultErrorMessage;
    String reportMethod;

    String reportEmail;

    List<Screenshot> screenShots = new ArrayList<Screenshot>();

    private String nameSpace;
    private String javaScript = "";
    private String os;
    private String url;

    public String getOs() {
        return os;
    }

    public String getReportMethod() {
        return reportMethod;
    }

    public void setReportMethod(String reportMethod) {
        this.reportMethod = reportMethod;
    }

    public String getReportEmail() {
        return reportEmail;
    }

    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    public String getJavaScript() {
        return javaScript;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSendFeedback() {
        return sendFeedback;
    }

    public void setSendFeedback(boolean sendFeedback) {
        this.sendFeedback = sendFeedback;
    }

    public boolean isFeedbackBySms() {
        return feedbackBySms;
    }

    public void setFeedbackBySms(boolean feedbackBySms) {
        this.feedbackBySms = feedbackBySms;
    }

    public String getSmsPhoneNumber() {
        return smsPhoneNumber;
    }

    public void setSmsPhoneNumber(String smsPhoneNumber) {
        this.smsPhoneNumber = smsPhoneNumber;
    }

    public boolean isFeedbackByMail() {
        return feedbackByMail;
    }

    public void setFeedbackByMail(boolean feedbackByMail) {
        this.feedbackByMail = feedbackByMail;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isFeedbackByPhone() {
        return feedbackByPhone;
    }

    public void setFeedbackByPhone(boolean feedbackByPhone) {
        this.feedbackByPhone = feedbackByPhone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSendScreenShot() {
        return sendScreenShot;
    }

    public void setSendScreenShot(boolean sendScreenShot) {
        this.sendScreenShot = sendScreenShot;
    }

    public List<String> getErrorTypes() {
        return errorTypes;
    }

    public void addErrorType(String errorType) {
        this.errorTypes.add(errorType);
    }

    public List<Screenshot> getScreenShots() {
        return screenShots;
    }

    public void addScreenShot(Screenshot ss) {
        this.screenShots.add(ss);
    }

    public IncidentReport() {
    }

    public String getDefaultErrorMessage() {
        return this.defaultErrorMessage;
    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }

    public void setNameSpace(String namespace) {
        this.nameSpace = namespace;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Application name: " + this.getApplicationName() + NEWLINE);
        if (this.url != null && url.length() > 0) {
            sb.append(url + " " + NEWLINE);
        }
        if (this.nameSpace != null && nameSpace.length() > 0) {
            sb.append("Portlet name: " + this.getNameSpace() + NEWLINE + NEWLINE);
        }
        String thrownErrorMessage = this.getDefaultErrorMessage();
        if (thrownErrorMessage != null && thrownErrorMessage.length() > 0) {
            sb.append("Felrapporten har triggats av fel: " + thrownErrorMessage + NEWLINE);
        }
        else {
            sb.append("Felrapporten har triggats av användare" + NEWLINE);
        }
        sb.append(NEWLINE + "Uppgifter inmatade av användaren" + NEWLINE);
        sb.append("- Typ av feedback: " + this.getReportType() + NEWLINE);
        if (this.getErrorTypes() != null) {
            List<String> errTypes = this.getErrorTypes();
            sb.append("- Typ av fel: ");
            for (String type : errTypes) {
                sb.append(type + ",");
            }
            sb.append(NEWLINE);
        }
        sb.append("- Förklara felet med egna ord: " + this.getDescription() + NEWLINE);

        sb.append("- Användaren vill ha feedback via: ");
        if (this.feedbackByMail) {
            sb.append("email ");
        }
        if (this.feedbackByPhone) {
            sb.append("telefon ");
        }
        if (this.feedbackBySms) {
            sb.append("SMS ");
        }
        sb.append(NEWLINE);
        if (this.getEmailAddress() != null) {
            sb.append("- Användaren email: " + this.getEmailAddress() + NEWLINE);
        }
        if (this.getPhoneNumber() != null) {
            sb.append("- Användaren telefon: " + this.getPhoneNumber() + NEWLINE);
        }
        if (this.getSmsPhoneNumber() != null) {
            sb.append("- Användaren SMS: " + this.getSmsPhoneNumber() + NEWLINE);
        }

        if (this.getScreenShots() != null) {
            List<Screenshot> files = this.getScreenShots();
            sb.append("- Bifogade skärmdumpar: ");
            for (Screenshot file : files) {
                sb.append(file.getFileName() + ", ");
            }
            sb.append(NEWLINE);
        }

        sb.append(NEWLINE + "Uppgifter automatgenererade" + NEWLINE);
        sb.append("- Användar ID: " + this.getUserId() + NEWLINE);
        sb.append("- IP Adress: " + this.getIpAddress() + NEWLINE);
        sb.append("- Browser: " + this.getBrowser() + NEWLINE);
        sb.append("- OS: " + this.getOs() + NEWLINE);
        sb.append("- Javascript: " + this.getJavaScript() + NEWLINE);
        sb.append("- Referer: " + this.getReferer() + NEWLINE);
        sb.append("- Timestamp: " + this.getTimeStamp() + NEWLINE);

        sb.append(NEWLINE + "Valt rapporteringsätt för applikationen: " + this.getReportMethod() + NEWLINE);
        sb.append("Email till applikationsansvarig: " + this.getReportEmail() + NEWLINE);

        return sb.toString();
    }

    public void setJavascript(String js) {
        this.javaScript = js;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public static void main(String[] args) {
        IncidentReport ir = new IncidentReport();
        System.out.println(ir);
    }

    public void setLink(String url) {
        this.url = url;
    }
}
