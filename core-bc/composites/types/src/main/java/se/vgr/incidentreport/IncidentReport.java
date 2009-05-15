package se.vgr.incidentreport;

import java.io.File;

public class IncidentReport {
	String reportType = "error";
	String[] errorTypes = new String[0];
	String timeStamp;
	String browser;
	String description;
	boolean sendFeedback;
	boolean feedbackBySms;
	String smsPhoneNumber;
	boolean feedbackByMail;
	String emailAddress;
	boolean feedbackByPhone;
	String phoneNumber; // might be different from the sms-phone-number above
	
	boolean sendScreenShot;
	File[] screenShots = new File[0];
	
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

	public String[] getErrorTypes() {
		return errorTypes;
	}

	public void setErrorTypes(String[] errorTypes) {
		this.errorTypes = errorTypes;
	}

	public File[] getScreenShots() {
		return screenShots;
	}

	public void setScreenShots(File[] screenShots) {
		this.screenShots = screenShots;
	}

	public IncidentReport(){}

	
	
	
}
