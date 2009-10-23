package se.vgr.incidentreport.pivotaltracker;

public class PTStory {
    String type;
    String name;
    String requestedBy;
    private String description;

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
