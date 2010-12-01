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

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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

    private boolean shouldContactUser;

    private String breadcrumb;

    /* User contact options */
    @Enumerated(EnumType.STRING)
    private UserContactOption contactOption;
    private String userEmail;
    private String userPhonenumber;
    private String userName;

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

    @OneToOne(cascade = CascadeType.ALL)
    private PlatformData platformData;

    @Transient
    private Long staticCaseCategoryId;
    @Transient
    private Long customCaseCategoryId;
    @Transient
    private List<Long> caseSubCategoryIds;

    @Override
    public Long getId() {
        return id;
    }

    /**
     * List of ways user can be contacted.
     */
    public enum UserContactOption {
        email("e-post"), telephone("Telefon");

        private String label;

        private UserContactOption(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /* Helper functions to map between enum names and labels */
        private static Map<UserContactOption, String> labelMap;

        static {
            labelMap = new TreeMap<UserContactOption, String>(new Comparator<UserContactOption>() {
                @Override
                public int compare(UserContactOption c1, UserContactOption c2) {
                    return c1.ordinal() - c2.ordinal();
                }
            });
            for (UserContactOption c : UserContactOption.values()) {
                labelMap.put(c, c.label);
            }
        }

        /**
         * Get a map linking human friendly labels to the enum constant names.
         * 
         * @return .
         */
        public static Map<UserContactOption, String> getLabelMap() {
            return labelMap;
        }
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

    public boolean isShouldContactUser() {
        return shouldContactUser;
    }

    public void setShouldContactUser(boolean shouldContactUser) {
        this.shouldContactUser = shouldContactUser;
    }

    public String getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(String breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public UserContactOption getContactOption() {
        return contactOption;
    }

    public void setContactOption(UserContactOption contactOption) {
        this.contactOption = contactOption;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhonenumber() {
        return userPhonenumber;
    }

    public void setUserPhonenumber(String userPhonenumber) {
        this.userPhonenumber = userPhonenumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PlatformData getPlatformData() {
        return platformData;
    }

    public void setPlatformData(PlatformData platformData) {
        this.platformData = platformData;
    }

    // --------------------------------------------------------------

    public Long getStaticCaseCategoryId() {
        return staticCaseCategoryId;
    }

    public void setStaticCaseCategoryId(Long staticCaseCategoryId) {
        this.staticCaseCategoryId = staticCaseCategoryId;
    }

    public Long getCustomCaseCategoryId() {
        return customCaseCategoryId;
    }

    public void setCustomCaseCategoryId(Long customCaseCategoryId) {
        this.customCaseCategoryId = customCaseCategoryId;
    }

    public List<Long> getCaseSubCategoryIds() {
        return caseSubCategoryIds;
    }

    public void setCaseSubCategoryIds(List<Long> caseSubCategoryIds) {
        this.caseSubCategoryIds = caseSubCategoryIds;
    }
}
