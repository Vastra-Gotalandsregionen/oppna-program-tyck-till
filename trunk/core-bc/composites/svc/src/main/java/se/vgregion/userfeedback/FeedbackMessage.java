package se.vgregion.userfeedback;


public class FeedbackMessage {

    private String reportType = "";
    private String trackerId;
    private String description;
    private String url;
    private String hyperlink;

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
        // return trackerId;
        return "Tyck_till_test_portlet"; // TODO: Change back.
    }

    /**
     * 
     * @param applicationName
     */
    public void setTrackerCategory(String applicationName) {
        this.trackerId = trackerId;
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
