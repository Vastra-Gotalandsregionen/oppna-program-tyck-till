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

package se.vgregion.userfeedback.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

/**
 * This class represents a feedback instance.
 * 
 * @author Robert de Bésche
 * 
 */
@Entity
public class UserFeedback extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = 8809380011250380574L;

    @Id
    @GeneratedValue
    private Long id;

    private String breadcrumb;

    /* User contact options */
    @Embedded
    private UserContact userContact;

    private String caseCategory;

    @ElementCollection
    private List<String> caseSubCategories;

    private String caseContact;

    /* Message */
    private String message;

    /* Screendumps */
    private boolean attachScreenDump;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USERFEEDBACK_ID")
    private Set<Attachment> attachments = new HashSet<Attachment>();

    @Embedded
    private PlatformData platformData;

    @Embedded
    private Backend caseBackend;

    @Transient
    private Long caseCategoryId;
    @Transient
    private List<Long> caseSubCategoryIds;
    /**
     * Hyperlink to project from Pivotal
     */
    @Transient
    private String hyperLink;

    @Override
    public Long getId() {
        return id;
    }

    public boolean isAttachScreenDump() {
        return attachScreenDump;
    }

    public void setAttachScreenDump(boolean attachScreenDump) {
        this.attachScreenDump = attachScreenDump;
    }

    public String getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(String caseCategory) {
        this.caseCategory = caseCategory;
    }

    public List<String> getCaseSubCategories() {
        return caseSubCategories;
    }

    public void setCaseSubCategories(List<String> caseSubCategories) {
        this.caseSubCategories = caseSubCategories;
    }

    public String getCaseContact() {
        return caseContact;
    }

    public void setCaseContact(String caseContact) {
        this.caseContact = caseContact;
    }

    public Backend getCaseBackend() {
        return caseBackend;
    }

    public void setCaseBackend(Backend caseBackend) {
        this.caseBackend = caseBackend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(String breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    public PlatformData getPlatformData() {
        return platformData;
    }

    public void setPlatformData(PlatformData platformData) {
        this.platformData = platformData;
    }

    // --------------------------------------------------------------

    public Long getCaseCategoryId() {
        return caseCategoryId;
    }

    public void setCaseCategoryId(Long staticCaseCategoryId) {
        this.caseCategoryId = staticCaseCategoryId;
    }

    public List<Long> getCaseSubCategoryIds() {
        return caseSubCategoryIds;
    }

    public void setCaseSubCategoryIds(List<Long> caseSubCategoryIds) {
        this.caseSubCategoryIds = caseSubCategoryIds;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (hyperLink != null && hyperLink.length() > 0) {
            sb.append(hyperLink + " " + "\n");
        }
        sb.append("\n" + "Uppgifter inmatade av användaren" + "\n");
        sb.append("- Förklara felet med egna ord: " + "\n" + getMessage());
        sb.append("\n\n");
        sb.append("- Typ av feedback: " + getCaseCategory() + "\n");

        sb.append("- Användaren vill ha feedback via: ");
        UserContact method = getUserContact();
        sb.append(method.getContactOption().getLabel());
        switch (method.getContactOption()) {
            case email:
                sb.append("- Användaren epost: " + method.getContactMethod() + "\n");
                break;
            case telephone:
                sb.append("- Användaren telefon: " + method.getContactMethod() + "\n");
                break;
            default:
                throw new RuntimeException("Unrecognized use contact option");
        }
        sb.append("\n");
        if (this.getAttachments() != null) {
            Collection<Attachment> attachments = this.getAttachments();
            sb.append("- Bifogade skärmdumpar: ");
            for (Attachment attachment : attachments) {
                sb.append(attachment.getFilename());
                sb.append(", ");
            }
            sb.append("\n");
        }
        sb.append("\n" + "Uppgifter automatgenererade" + "\n");
        PlatformData userPlatform = getPlatformData();
        sb.append("- Användar ID: " + userPlatform.getUserId() + "\n");
        sb.append("- IP Adress: " + userPlatform.getIpAddress() + "\n");
        sb.append("- Browser: " + userPlatform.getBrowser() + "\n");
        sb.append("- OS: " + userPlatform.getOperatingSystem() + "\n");
        sb.append("- Referer: " + userPlatform.getReferer() + "\n");
        sb.append("- Timestamp: " + userPlatform.getTimeStamp() + "\n");

        sb.append("\n");
        return sb.toString();
    }

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }
}
