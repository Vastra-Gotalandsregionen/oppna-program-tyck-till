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

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main domain object that handles the user feedback.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class UserFeedback extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = 8809380011250380574L;

    @Id
    @GeneratedValue
    private Long id;

    private String breadcrumb;


    private String caseCategory;

    @ElementCollection
    private List<String> caseSubCategories;

    @Column(length = 70)
    @Size(max = 70, message = "{validation.feedback.caseTitle}")
    private String caseTitle;

    /* Message */
    @Column(length = 2048)
    @Size(max = 2048, message = "{validation.feedback.message}")
    private String caseMessage;

    /* User contact options */
    @Valid
    @Embedded
    private UserContact userContact;

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

    public Backend getCaseBackend() {
        return caseBackend;
    }

    public void setCaseBackend(Backend caseBackend) {
        this.caseBackend = caseBackend;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseMessage() {
        return caseMessage;
    }

    public void setCaseMessage(String caseMessage) {
        this.caseMessage = caseMessage;
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

    // --------------------------------------------------------------

    public String getHyperLink() {
        return hyperLink;
    }

    public void setHyperLink(String hyperLink) {
        this.hyperLink = hyperLink;
    }
}
