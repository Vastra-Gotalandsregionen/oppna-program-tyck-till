package se.vgregion.userfeedback.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import se.vgregion.userfeedback.domain.Backend;
import se.vgregion.userfeedback.domain.CustomCategory;
import se.vgregion.userfeedback.domain.CustomSubCategory;
import se.vgregion.userfeedback.domain.FormTemplate;
import se.vgregion.userfeedback.domain.FormTemplateRepository;
import se.vgregion.userfeedback.domain.StaticCategory;
import se.vgregion.userfeedback.domain.StaticCategoryRepository;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@SessionAttributes({ "formTemplate", "customCategory" })
public class TyckTillAdminController {

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    private StaticCategoryRepository staticCategoryRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/KontaktaOss/TemplateList")
    public String listView(ModelMap model) {

        Collection<FormTemplate> templates = formTemplateRepository.findAll();
        model.addAttribute("templateList", templates);

        return "TemplateList";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/KontaktaOss/TemplateEdit")
    public String viewTemplates(@RequestParam(value = "templateId", required = false) Long templateId,
            ModelMap model) {
        FormTemplate template;
        if (templateId == null) {
            template = (FormTemplate) model.get("formTemplate");

            if (template == null) {
                template = new FormTemplate();
            }
        } else {
            template = formTemplateRepository.find(templateId);
        }
        model.addAttribute("formTemplate", template);

        // expose CustomSubCategories
        CustomCategory customCategory = template.getCustomCategory();
        if (customCategory == null) {
            customCategory = new CustomCategory();
            template.setCustomCategory(customCategory);
        }
        model.addAttribute("customCategory", customCategory);

        List<CustomSubCategory> subCategories = customCategory.getCustomSubCategories();
        if (subCategories == null) {
            subCategories = new ArrayList<CustomSubCategory>();
            customCategory.setCustomSubCategories(subCategories);
        }
        model.addAttribute("customSubCategories", subCategories);

        // expose StaticCategories
        StaticCategory contentCategory = staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_CONTENT_CATEGORY);
        StaticCategory functionCategory = staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_FUNCTION_CATEGORY);
        StaticCategory otherCategory = staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_OTHER_CATEGORY);
        model.addAttribute("contentCategory", contentCategory);
        model.addAttribute("functionCategory", functionCategory);
        model.addAttribute("otherCategory", otherCategory);

        return "TemplateEdit";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String addTemplate(@Valid @ModelAttribute("formTemplate") FormTemplate formTemplate,
            BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            return "TemplateEdit";
        }

        CustomCategory customCategory = formTemplate.getCustomCategory();
        if (customCategory != null) {
            for (Iterator<CustomSubCategory> it = customCategory.getCustomSubCategories().iterator(); it.hasNext();) {
                CustomSubCategory subCategory = it.next();
                if (StringUtils.isBlank(subCategory.getName())) {
                    it.remove();
                }
            }
        }

        formTemplate.setLastChanged(new Date());
        if (formTemplate.getId() == null) {
            formTemplateRepository.persist(formTemplate);
        } else {
            formTemplateRepository.merge(formTemplate);
        }

        status.setComplete();

        return "redirect:TemplateList";
    }

    /**
     * Edit CustomCategory without persis. FormTemplate has to be handled to allow preserving data not stored yet.
     * 
     * @param formTemplate
     *            - main backing bean.
     * @return - view with edit form.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/KontaktaOss/CustomCategoryEdit")
    public String editCustomCategory(@ModelAttribute("formTemplate") FormTemplate formTemplate) {

        List<CustomSubCategory> subCategories = formTemplate.getCustomCategory().getCustomSubCategories();

        for (int i = 0; i < 5; i++) {
            CustomSubCategory customSubCategory = new CustomSubCategory();
            customSubCategory.setBackend(new Backend());

            subCategories.add(customSubCategory);
        }

        return "CustomCategoryEdit";
    }

    /**
     * Redirect back to TemplateEdit to continue working with the formTemplate.
     * 
     * @param formTemplate
     *            - the main backing bean.
     * @return - view for FormTemplate edit.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/KontaktaOss/CustomCategoryUpdate")
    public String updateCustomCategory(@ModelAttribute("formTemplate") FormTemplate formTemplate) {
        for (Iterator<CustomSubCategory> it = formTemplate.getCustomCategory().getCustomSubCategories().iterator(); it
                .hasNext();) {
            CustomSubCategory subCategory = it.next();
            if (StringUtils.isBlank(subCategory.getName())) {
                it.remove();
            }
        }

        return "redirect:TemplateEdit";
    }

}