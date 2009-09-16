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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IncidentReport {
    String reportType = "error";
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

    String reportEmail;

    List<File> screenShots = new ArrayList<File>();
    private String nameSpace;

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

    public List<File> getScreenShots() {
        return screenShots;
    }

    public void addScreenShot(File screenShot) {
        this.screenShots.add(screenShot);
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
        sb.append("Application name: " + this.getApplicationName() + "<br/>");
        sb.append("Namespace: " + this.getNameSpace() + "<br/>");
        sb.append("Description: " + this.getDescription() + "<br/>");
        sb.append("IP Address: " + this.getIpAddress() + "<br/>");
        sb.append("User email: " + this.getEmailAddress() + "<br/>");
        sb.append("Browser: " + this.getBrowser() + "<br/>");
        sb.append("Referer: " + this.getReferer() + "<br/>");
        sb.append("Error message: " + this.getDefaultErrorMessage() + "<br/>");
        sb.append("Timestamp: " + this.getTimeStamp() + "<br/>");
        sb.append("User ID: " + this.getUserId() + "<br/>");

        return sb.toString();
    }
}
