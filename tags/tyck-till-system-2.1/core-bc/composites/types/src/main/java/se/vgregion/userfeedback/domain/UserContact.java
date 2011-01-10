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
 */

package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.valueobject.AbstractValueObject;
import se.vgregion.userfeedback.validation.CheckUserContact;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Domain object that handles contact information for the user that made the report.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@CheckUserContact
@Embeddable
public class UserContact extends AbstractValueObject<UserContact> implements Serializable {

    private static final long serialVersionUID = -1907504105440943726L;

    private boolean shouldContactUser;

    @Enumerated(EnumType.STRING)
    private UserContactOption contactOption;

    private String contactMethod;
    private String userName;

    public Boolean getShouldContactUser() {
        return shouldContactUser;
    }

    public void setShouldContactUser(Boolean shouldContactUser) {
        this.shouldContactUser = shouldContactUser;
    }

    public UserContactOption getContactOption() {
        return contactOption;
    }

    public void setContactOption(UserContactOption contactOption) {
        this.contactOption = contactOption;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * List of ways user can be contacted.
     */
    public enum UserContactOption {
        email("E-post"),
        telephone("Telefon");

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
}
