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

package se.vgregion.incidentreport.domain;

import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a feedback instance.
 * 
 * @author Robert de Bésche
 * 
 */
public class UserFeedback extends FeedbackForm implements Serializable {

    private static final long serialVersionUID = 8809380011250380574L;

    /*
     * Deal with case subject and sub-headings.
     */
    private CaseSubject caseSubject;

    /* Helthcare issue subheading */

    private HealthcareSubHeadings healthCareSubHeadings;

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
    private List<Byte[]> screenDumps;

    /**
     * @author Arakun
     * 
     */
    public enum CaseSubject {
        webpageContentRelated(CONTENT_RELATED),
        webpageFunctionRelated(FUNCTION_RELATED),
        healthcareRelated(HEALTHCARE_RELATED),
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
                labelMap.put(c.label, c.name());
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
    public enum HealthcareSubHeadings {
        fees(HEALTHCARE_FEES), healthcareAbroad(HEALTHCARE_ABROAD), careAssurances(HEALTHCARE_ASSURANCES), waitingTime(
                HEALTHCARE_WAITING_TIMES), freedomOfChoice(HEALTHCARE_FREEDOM_OF_CHOICE), otherHealthcareIssues(HEALTHCARE_OTHER);

        private String label;

        private HealthcareSubHeadings(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        /* Helper functions to map between enum names and labels */
        private static Map<String,String> labelMap;

        static  {
            labelMap = new HashMap<String,String>();
            for(HealthcareSubHeadings c : HealthcareSubHeadings.values())  {
                labelMap.put(c.label, c.name());
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
    private static final String HEALTHCARE_FEES = "Avgifter";
    private static final String HEALTHCARE_ABROAD = "Utomlandsvård";
    private static final String HEALTHCARE_ASSURANCES = "Vårdgarantier";
    private static final String HEALTHCARE_WAITING_TIMES = "Väntetider";
    private static final String HEALTHCARE_FREEDOM_OF_CHOICE = "Valfrihet i vården";
    private static final String HEALTHCARE_OTHER = "Övriga vårdfrågor";

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

    public HealthcareSubHeadings getHealthCareSubHeadings() {
        return healthCareSubHeadings;
    }

    public void setHealthCareSubHeadings(HealthcareSubHeadings healthCareSubHeadings) {
        this.healthCareSubHeadings = healthCareSubHeadings;
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

    public List<Byte[]> getScreenDumps() {
        return screenDumps;
    }

    public void setScreenDumps(List<Byte[]> screenDumps) {
        this.screenDumps = screenDumps;
    }

    public void addScreenDump(Byte[] screenDump) {
        this.screenDumps.add(screenDump);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
