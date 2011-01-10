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

package se.vgregion.userfeedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.userfeedback.domain.CustomSubCategory;
import se.vgregion.userfeedback.domain.CustomSubCategoryRepository;

import java.beans.PropertyEditorSupport;

/**
 * Custom spring converter to allow CustomSubCategory binding.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class CustomSubCategoryPropertyEditor extends PropertyEditorSupport {
    @Autowired
    private CustomSubCategoryRepository customSubCategoryRepository;

    /**
     * Lookup object from incoming String id representation.
     *
     * @param incoming - CustomSubCategory id as String.
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String incoming) throws IllegalArgumentException {
        Long id = 0l;
        try {
            id = Long.parseLong(incoming);
        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Binding error. Invalid id:" + incoming, e);
        }

        CustomSubCategory subCategory = customSubCategoryRepository.find(id);
        if (subCategory != null) {
            setValue(subCategory);
        } else {
//            throw new IllegalArgumentException("Binding error. Cannot find customSubCategory.");
        }
    }

    /**
     * Convert subcategory back to it's String representation.
     *
     * @return - CustomSubCategory id as String.
     */
    @Override
    public String getAsText() {
        CustomSubCategory subCategory = (CustomSubCategory) getValue();
        if (subCategory != null) {
            return subCategory.getId().toString();
        } else {
            return "";
        }
    }
}
