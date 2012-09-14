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

package se.vgregion.incidentreport;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import se.vgregion.incidentreport.domain.TyckTillSettings;

public class TyckTillSettingsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TyckTillSettings.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TyckTillSettings raindanceSettings = (TyckTillSettings) target;
        if (raindanceSettings.getTestAttribute() == null) {
            errors.rejectValue("testAttribute", "testAttribute.error", "Test attribute is null");
        }

    }
}
