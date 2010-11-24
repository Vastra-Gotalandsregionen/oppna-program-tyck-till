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
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Robert de Bésche
 * 
 */
public class FeedbackForm implements Serializable {

    private static final long serialVersionUID = 8809380011250380575L;

    private boolean shouldContactUser;

    private String breadcrumb;

    /* User contact options */
    private UserContactOption contactOption;
    private String userEmail;
    private String userPhonenumber;
    private String userName;

    public boolean isShouldContactUser() {
        return shouldContactUser;
    }

    public void setShouldContactUser(boolean shouldContactUser) {
        this.shouldContactUser = shouldContactUser;
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

    public UserContactOption getContactOption() {
        return contactOption;
    }

    public void setContactOption(UserContactOption contactOption) {
        this.contactOption = contactOption;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(String breadcrumb) {
        this.breadcrumb = breadcrumb;
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
}
