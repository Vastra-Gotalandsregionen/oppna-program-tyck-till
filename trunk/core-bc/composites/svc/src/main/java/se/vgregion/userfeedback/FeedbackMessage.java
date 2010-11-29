package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackMessage {

    private String reportType = "";
    private List<String> errorTypes = new ArrayList<String>();
    private String timeStamp;
    private String browser;
    private String userId;
    private String referer;
    private String ipAddress;
    private String applicationName;
    private String description;
    private String nameSpace;
    private String javaScript = "";
    private String os;
    private String url;
    private String hyperlink;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<String> getErrorTypes() {
        return errorTypes;
    }

    /**
     * If the incident report was created because of a java error, these are added via this method.
     * 
     * @param errorType
     *            The java error that was thrown
     */
    public void setErrorTypes(List<String> errorTypes) {
        this.errorTypes = errorTypes;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getJavaScript() {
        return javaScript;
    }

    public void setJavascript(String js) {
        this.javaScript = js;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String namespace) {
        this.nameSpace = namespace;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setHyperlink(String link) {
        this.hyperlink = link;
    }

    public String getHyperlink() {
        return hyperlink;
    }

}
