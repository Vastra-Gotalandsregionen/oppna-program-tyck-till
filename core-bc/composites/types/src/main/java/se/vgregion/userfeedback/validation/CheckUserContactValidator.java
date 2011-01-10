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

package se.vgregion.userfeedback.validation;

import org.apache.commons.lang.StringUtils;
import se.vgregion.userfeedback.domain.UserContact;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSR-303 validator for UserContact.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class CheckUserContactValidator implements ConstraintValidator<CheckUserContact, UserContact> {

    /**
     * Initialization.
     *
     * @param constraintAnnotation - access to annotation.
     */
    @Override
    public void initialize(CheckUserContact constraintAnnotation) {
    }

    /**
     * Contact data validation.
     * When a user has specified that he want to be contacted, make sure the means to contact
     * has been provided.
     *
     * @param userContact - the instance to be validated.
     * @param context     - validation context.
     * @return - validation result.
     */
    @Override
    public boolean isValid(UserContact userContact, ConstraintValidatorContext context) {

        if (userContact == null) {
            return true;
        }

        if (userContact.getShouldContactUser()) {
            boolean isUserNameValid = StringUtils.isNotBlank(userContact.getUserName());
            boolean isContactMethodValid = StringUtils.isNotBlank(userContact.getContactMethod());

            if (!isUserNameValid || !isContactMethodValid) {
                context.disableDefaultConstraintViolation();

                if (!isUserNameValid) {
                    context.buildConstraintViolationWithTemplate("{vgr.tycktill.usercontact.username}")
                            .addNode("userName").addConstraintViolation();
                }
                if (!isContactMethodValid) {
                    if (userContact.getContactOption() == null) {
                        context.buildConstraintViolationWithTemplate("{vgr.tycktill.usercontact.contactmethod}")
                                .addNode("contactMethod").addConstraintViolation();
                    } else {
                        switch (userContact.getContactOption()) {
                            case email:
                                context.buildConstraintViolationWithTemplate("{vgr.tycktill.usercontact.contactmethod.email}")
                                        .addNode("contactMethod").addConstraintViolation();
                                break;
                            case telephone:
                                context.buildConstraintViolationWithTemplate("{vgr.tycktill.usercontact.contactmethod.telephone}")
                                        .addNode("contactMethod").addConstraintViolation();
                                break;
                            default:
                                context.buildConstraintViolationWithTemplate("{vgr.tycktill.usercontact.contactmethod}")
                                        .addNode("contactMethod").addConstraintViolation();
                                break;
                        }
                    }
                }

                return false;
            }
        }

        return true;
    }
}
