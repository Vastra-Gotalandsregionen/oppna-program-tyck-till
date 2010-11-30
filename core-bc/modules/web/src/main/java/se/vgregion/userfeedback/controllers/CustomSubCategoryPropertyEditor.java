package se.vgregion.userfeedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import se.vgregion.userfeedback.domain.CustomSubCategory;
import se.vgregion.userfeedback.domain.CustomSubCategoryRepository;

import java.beans.PropertyEditorSupport;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class CustomSubCategoryPropertyEditor extends PropertyEditorSupport {
    @Autowired
    CustomSubCategoryRepository customSubCategoryRepository;

    @Override
    public void setAsText(String incoming) throws IllegalArgumentException {
        Long id;
        try {
            id = Long.parseLong(incoming);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Binding error. Invalid id:"+incoming);
        }

        CustomSubCategory subCategory = customSubCategoryRepository.find(id);
        if (subCategory != null) {
            setValue(subCategory);
        } else {
            throw new IllegalArgumentException("Binding error. Cannot find customSubCategory.");
        }
    }

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
