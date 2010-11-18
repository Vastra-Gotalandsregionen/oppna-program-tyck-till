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

import java.io.Serializable;

/**
 * This class represents a feedback instance.
 * 
 * @author Robert de Bésche
 * 
 */
public class FeedbackForm implements Serializable {

    private static final long serialVersionUID = 8809380011250380575L;

    private boolean shouldContactUser;

    /* User contact options */
    private boolean contactByEmail;
    private boolean contactByPhonenumber;
    private String userEmail;
    private String userPhonenumber;

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

    public boolean isContactByEmail() {
        return contactByEmail;
    }

    public void setContactByEmail(boolean contactByEmail) {
        this.contactByEmail = contactByEmail;
    }

    public boolean isContactByPhonenumber() {
        return contactByPhonenumber;
    }

    public void setContactByPhonenumber(boolean contactByPhonenumber) {
        this.contactByPhonenumber = contactByPhonenumber;
    }

    /**
     * List of ways user can be contacted.
     */
    enum UserContactOptions {
        email("e-post"), telephone("Telefon");
        private String displayText;

        private UserContactOptions(String displaytext) {
            this.displayText = displaytext;
        }

        public String getDisplayText() {
            return displayText;
        }
    }
}
