package se.vgregion.userfeedback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import se.vgregion.userfeedback.domain.FormTemplate;
import se.vgregion.userfeedback.domain.FormTemplateRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@SessionAttributes("formTemplate")
public class TyckTillAdminController {

    @Autowired
    private FormTemplateRepository formTemplateRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/KontaktaOss/TemplateList")
    public String listView(ModelMap model) {

        Collection<FormTemplate> templates = formTemplateRepository.findAll();
        model.addAttribute("templateList", templates);

        return "KontaktaOssTemplateList";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/KontaktaOss/TemplateEditor")
    public String viewTemplates(@RequestParam(value="templateId", required = false) Long templateId,
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

        return "KontaktaOssTemplateEdit";
    }


    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String addTemplate(@Valid @ModelAttribute("formTemplate") FormTemplate formTemplate,
                              BindingResult result,
                              SessionStatus status,
                              ModelMap model) {
        if (result.hasErrors()) {
            return "KontaktaOssTemplateEdit";
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
}