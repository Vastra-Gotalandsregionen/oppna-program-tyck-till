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
import java.util.*;

/**
 * This class represents a feedback instance.
 * 
 * @author Robert de Bésche
 * 
 */
public class UserFeedback implements Serializable {

    private static final long serialVersionUID = 8809380011250380574L;

    private boolean shouldContactUser;

    private String breadcrumb;

    /* User contact options */
    private UserContactOption contactOption;
    private String userEmail;
    private String userPhonenumber;
    private String userName;

    /*
     * Deal with case subject and sub-headings.
     */
    private CaseSubject caseSubject;

    /* Helthcare issue subheading */

    private HealthcareCategory healthcareCategory;

    /* Webpage function subheadings */
    private boolean pageDoesNotExist;
    private boolean gotErrorMessage;
    private boolean pageDoesNotLoad;
    private boolean doesNotUnderstandFunction;

    /* Webpage content subheadings */
    private boolean missingContent;
    private boolean wrongContent;
    private boolean cannotFindInformation;

    /* Message */
    private String message;

    /* Screendumps */
    private boolean attachScreenDump;

    private Attachments attachments = new Attachments();


    /**
     * @author Arakun
     * 
     */
    public enum CaseSubject {
        webpageContent(CONTENT_RELATED),
        webpageFunction(FUNCTION_RELATED),
        healthcare(HEALTHCARE_RELATED),
        other(OTHER_RELATED);

        private String label;

        CaseSubject(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public String getName() {
            return name();
        }

        /* Helper functions to map between enum names and labels */
        private static Map<String,String> labelMap;

        static  {
            labelMap = new HashMap<String,String>();
            for(CaseSubject c : CaseSubject.values())  {
                labelMap.put(c.name(), c.label);
            }
        }

        /**
         * Get a map linking human friendly labels to the enum constant names.
         *
         * @return  .
         */
        public static Map<String,String> getLabelMap()   {
            return labelMap;
        }
    }

    /* Human friendly names for enum constants */
    private static final String CONTENT_RELATED = "Webbplatsens innehåll";
    private static final String FUNCTION_RELATED = "Webbplatsens funktion";
    private static final String HEALTHCARE_RELATED = "Vården";
    private static final String OTHER_RELATED = "Övrigt";

    /**
     * Sub-categories for the 'healthCareRelated' option of {@code CaseSubject}
     * 
     * @author Arakun
     * 
     */
    public enum HealthcareCategory {
        fees(HEALTHCARE_FEES), healthcareAbroad(HEALTHCARE_ABROAD), careAssurances(HEALTHCARE_ASSURANCES), waitingTime(
                HEALTHCARE_WAITING_TIMES), freedomOfChoice(HEALTHCARE_FREEDOM_OF_CHOICE), otherHealthcareIssues(HEALTHCARE_OTHER);

        private String label;

        private HealthcareCategory(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /* Helper functions to map between enum names and labels */
        private static Map<HealthcareCategory,String> labelMap;

        static  {
            labelMap = new TreeMap<HealthcareCategory, String>(new Comparator<HealthcareCategory> (){
                @Override
                public int compare(HealthcareCategory h1, HealthcareCategory h2) {
                    return h1.ordinal() - h2.ordinal();
                }
            });
            for(HealthcareCategory c : HealthcareCategory.values())  {
                labelMap.put(c, c.label);
            }
        }

        /**
         * Get a map linking human friendly labels to the enum constant names.
         *
         * @return  .
         */
        public static Map<HealthcareCategory, String> getLabelMap()   {
            return labelMap;
        }
    }

    /* Human friendly names for enum constants */
    private static final String HEALTHCARE_FEES = "Avgifter";
    private static final String HEALTHCARE_ABROAD = "Utomlandsvård";
    private static final String HEALTHCARE_ASSURANCES = "Vårdgarantier";
    private static final String HEALTHCARE_WAITING_TIMES = "Väntetider";
    private static final String HEALTHCARE_FREEDOM_OF_CHOICE = "Valfrihet i vården";
    private static final String HEALTHCARE_OTHER = "Övriga vårdfrågor";

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
        private static Map<UserContactOption,String> labelMap;

        static  {
            labelMap = new TreeMap<UserContactOption, String>(new Comparator<UserContactOption>(){
                @Override
                public int compare(UserContactOption c1, UserContactOption c2) {
                    return c1.ordinal() - c2.ordinal();
                }
            });
            for(UserContactOption c : UserContactOption.values())  {
                labelMap.put(c, c.label);
            }
        }

        /**
         * Get a map linking human friendly labels to the enum constant names.
         *
         * @return  .
         */
        public static Map<UserContactOption, String> getLabelMap()   {
            return labelMap;
        }
    }


    public CaseSubject getCaseSubject() {
        return caseSubject;
    }

    public void setCaseSubject(CaseSubject caseSubject) {
        this.caseSubject = caseSubject;
    }

    public boolean isAttachScreenDump() {
        return attachScreenDump;
    }

    public void setAttachScreenDump(boolean attachScreenDump) {
        this.attachScreenDump = attachScreenDump;
    }

    public HealthcareCategory getHealthcareCategory() {
        return healthcareCategory;
    }

    public void setHealthcareCategory(HealthcareCategory healthcareCategory) {
        this.healthcareCategory = healthcareCategory;
    }

    public boolean isPageDoesNotExist() {
        return pageDoesNotExist;
    }

    public void setPageDoesNotExist(boolean pageDoesNotExist) {
        this.pageDoesNotExist = pageDoesNotExist;
    }

    public boolean isGotErrorMessage() {
        return gotErrorMessage;
    }

    public void setGotErrorMessage(boolean gotErrorMessage) {
        this.gotErrorMessage = gotErrorMessage;
    }

    public boolean isPageDoesNotLoad() {
        return pageDoesNotLoad;
    }

    public void setPageDoesNotLoad(boolean pageDoesNotLoad) {
        this.pageDoesNotLoad = pageDoesNotLoad;
    }

    public boolean isDoesNotUnderstandFunction() {
        return doesNotUnderstandFunction;
    }

    public void setDoesNotUnderstandFunction(boolean doesNotUnderstandFunction) {
        this.doesNotUnderstandFunction = doesNotUnderstandFunction;
    }

    public boolean isMissingContent() {
        return missingContent;
    }

    public void setMissingContent(boolean missingContent) {
        this.missingContent = missingContent;
    }

    public boolean isWrongContent() {
        return wrongContent;
    }

    public void setWrongContent(boolean wrongContent) {
        this.wrongContent = wrongContent;
    }

    public boolean isCannotFindInformation() {
        return cannotFindInformation;
    }

    public void setCannotFindInformation(boolean cannotFindInformation) {
        this.cannotFindInformation = cannotFindInformation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
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
}
