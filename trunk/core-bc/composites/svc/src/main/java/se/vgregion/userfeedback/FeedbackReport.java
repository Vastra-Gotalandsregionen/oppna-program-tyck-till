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

package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackReport {
    public static final String NEWLINE = "\n";
    private String defaultErrorMessage;
    /* e-postadress till applikationsansvarig */
    private String reportEmail;

    public enum UserContactMethod {
        bySMS, byPhone, byEmail
    }

    public enum ReportMethod {
        usd, email, pivotal
    }

    private List<ReportMethod> reportMethods;
    private Map<UserContactMethod, Object> userContactMethods;
    private List<Screenshot> screenShots = new ArrayList<Screenshot>();
    private FeedbackMessage message;
    private PlatformData userPlatform;

    /**
     * Public constructor for the class.
     */
    public FeedbackReport(FeedbackMessage message, List<ReportMethod> reportMethods,
            Map<UserContactMethod, Object> contactMethods, PlatformData platform) {
        this.message = message;
        this.reportMethods = reportMethods;
        this.userContactMethods = contactMethods;
        this.userPlatform = platform;
    }

    /**
     * List of one or more tracking systems this report should start an instance in.
     * 
     */
    public List<ReportMethod> getReportMethods() {
        return reportMethods;
    }

    /**
     * Map containing the method user can be contacted an corresponding contact data (i.e. phonenumber, email)
     * 
     * @return
     */
    public Map<UserContactMethod, Object> getUserContactMethod() {
        return userContactMethods;
    }

    public PlatformData getUserPlatform() {
        return userPlatform;
    }

    public String getReportEmail() {
        return reportEmail;
    }

    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    public boolean isSendScreenShot() {
        return screenShots.size() != 0;
    }

    public List<se.vgregion.userfeedback.Screenshot> getScreenShots() {
        return screenShots;
    }

    /**
     * Adds a screenshot to be emailed, sent to USD, or attached to the user story in pivotal tracker.
     * 
     * @param ss
     *            Screenshot object
     */
    public void addScreenShot(Screenshot ss) {
        this.screenShots.add(ss);
    }

    public String getDefaultErrorMessage() {
        return this.defaultErrorMessage;
    }

    public void setDefaultErrorMessage(String defaultErrorMessage) {
        this.defaultErrorMessage = defaultErrorMessage;
    }

    public FeedbackMessage getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Application name: " + message.getApplicationName() + NEWLINE);
        if (message.getUrl() != null && message.getUrl().length() > 0) {
            sb.append(message.getUrl() + " " + NEWLINE);
        }
        String thrownErrorMessage = this.getDefaultErrorMessage();
        if (thrownErrorMessage != null && thrownErrorMessage.length() > 0) {
            sb.append("Felrapporten har triggats av fel: " + thrownErrorMessage + NEWLINE);
        } else {
            sb.append("Felrapporten har triggats av användare" + NEWLINE);
        }
        sb.append(NEWLINE + "Uppgifter inmatade av användaren" + NEWLINE);
        sb.append("- Förklara felet med egna ord: " + message.getDescription() + NEWLINE);
        sb.append(NEWLINE);
        sb.append("- Typ av feedback: " + message.getReportType() + NEWLINE);
        if (message.getErrorTypes() != null) {
            List<String> errTypes = message.getErrorTypes();
            sb.append("- Typ av fel: ");
            for (String type : errTypes) {
                sb.append(type + ",");
            }
            sb.append(NEWLINE);
        }

        sb.append("- Användaren vill ha feedback via: ");
        for (Map.Entry<UserContactMethod, Object> entry : userContactMethods.entrySet()) {
            sb.append(entry.getKey());
            switch (entry.getKey()) {
                case byEmail:
                    sb.append("- Användaren email: " + entry.getValue() + NEWLINE);
                    break;
                case byPhone:
                    sb.append("- Användaren telefon: " + entry.getValue() + NEWLINE);
                    break;
                case bySMS:
                    sb.append("- Användaren SMS: " + entry.getValue() + NEWLINE);
                    break;
                default:
                    throw new RuntimeException("Unrecognized use contact option");
            }
        }
        sb.append(NEWLINE);

        if (this.getScreenShots() != null) {
            List<se.vgregion.userfeedback.Screenshot> files = this.getScreenShots();
            sb.append("- Bifogade skärmdumpar: ");
            for (se.vgregion.userfeedback.Screenshot file : files) {
                sb.append(file.getFileName() + ", ");
            }
            sb.append(NEWLINE);
        }

        sb.append(NEWLINE + "Uppgifter automatgenererade" + NEWLINE);
        sb.append("- Användar ID: " + userPlatform.getUserId() + NEWLINE);
        sb.append("- IP Adress: " + userPlatform.getIpAddress() + NEWLINE);
        sb.append("- Browser: " + userPlatform.getBrowser() + NEWLINE);
        sb.append("- OS: " + userPlatform.getOperatingSystem() + NEWLINE);
        sb.append("- Referer: " + userPlatform.getReferer() + NEWLINE);
        sb.append("- Timestamp: " + userPlatform.getTimeStamp() + NEWLINE);

        sb.append(NEWLINE + "Valt rapporteringsätt för applikationen: ");
        for (UserContactMethod method : userContactMethods.keySet()) {
            sb.append(method + ", ");
        }
        sb.append(NEWLINE);
        sb.append("Email till applikationsansvarig: " + this.getReportEmail() + NEWLINE);
        return sb.toString();
    }
}
