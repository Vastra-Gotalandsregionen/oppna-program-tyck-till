package se.vgregion.userfeedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackMessage {

    private String reportType = "";
    private List<String> errorTypes = new ArrayList<String>();
    private String applicationName;
    private String description;
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
