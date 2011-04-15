package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.valueobject.AbstractValueObject;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Domain object that carries data about the user platform. It would typically be populated
 * with information from the user's HTTP request headers.
 * 
 * @author Arakun
 * 
 */
@Embeddable
public class PlatformData extends AbstractValueObject<PlatformData> implements Serializable {
    private static final long serialVersionUID = 6398230873747213549L;

    private String userId;
    private String browser;
    private String operatingSystem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    private String ipAddress;
    private String forwardedIpAddress;

    private String referer;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Date getTimeStamp() {
        return new Date(timeStamp.getTime());
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = new Date(timeStamp.getTime());
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getForwardedIpAddress() {
        return forwardedIpAddress;
    }

    public void setForwardedIpAddress(String forwardedIpAddress) {
        this.forwardedIpAddress = forwardedIpAddress;
    }
}
