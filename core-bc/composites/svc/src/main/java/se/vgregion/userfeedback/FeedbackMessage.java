package se.vgregion.userfeedback;

public class FeedbackMessage {

    private String reportType = "";
    private String trackerCategory;
    private String description;
    private String url;
    private String hyperlink;
    private String reportEmail;

    public String getReportEmail() {
        return reportEmail;
    }

    public void setReportEmail(String reportEmail) {
        this.reportEmail = reportEmail;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Returns the tracker id. This is used to determine which category to log the report to on the project
     * tracker.
     * 
     * @return
     */
    public String getTrackerCategory() {
        return trackerCategory;
    }

    /**
     * 
     * @param applicationName
     */
    public void setTrackerCategory(String trackerCategory) {
        this.trackerCategory = trackerCategory;
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
