package se.vgregion.userfeedback.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import se.vgregion.userfeedback.domain.*;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@RequestMapping(value = {"/KontaktaOss"})
@SessionAttributes("userFeedback")
public class TyckTillController {
    private String mainHeading = "Kontakta oss";
    private String leadText = "Här kan du lämna synpunkter och ställa frågor om webbplatsen. Har du frågor om vården kan du ställa dem här också.";

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CustomSubCategory.class, new CustomSubCategoryPropertyEditor());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "formName", required = false) String formName,
                            @RequestParam(value = "breadcrumb", required = false) String breadcrumb,
                            ModelMap model) {


        FormTemplate template = lookupFormTemplate(formName);
        model.addAttribute("template", template);

        UserFeedback userFeedback;
        if (!model.containsKey("userFeedback")) {
            userFeedback = new UserFeedback();
            userFeedback.setCaseCategory(null);
            model.addAttribute("userFeedback", userFeedback);
        } else {
            userFeedback = (UserFeedback) model.get("userFeedback");
        }
        userFeedback.setBreadcrumb(breadcrumb);

        model.addAttribute("contactOptions", UserFeedback.UserContactOption.getLabelMap());

        return "KontaktaOss";

    }

    private FormTemplate lookupFormTemplate(String formName) {
        FormTemplate template;
        try {
            template = formTemplateRepository.find(formName);
        } catch (NoResultException nrex) {
            template = formTemplateRepository.find("default");
        }

        return template;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String sendUserFeedback(@ModelAttribute("userFeedback") UserFeedback userFeedback,
                                   @RequestParam("formTemplateId") Long formTemplateId,
                                   MultipartHttpServletRequest multipartRequest,
                                   SessionStatus status,
                                   ModelMap model) {
        System.out.println("Sending...");

        for (Iterator<String> filenameIterator = multipartRequest.getFileNames(); filenameIterator.hasNext();) {
            String fileName = filenameIterator.next();

            processAttachment(multipartRequest.getFile(fileName), userFeedback);
        }

        System.out.println("attachmentSize: " + userFeedback.getAttachments().size());

        for (Map.Entry<String, Object> entry : model.entrySet()) {
            System.out.println("Entry: " + entry.getKey());
        }

        String caseContact = lookupCaseContact(userFeedback, formTemplateId);
        userFeedback.setCaseContact(caseContact);

        // Log UserFeedback in db
        if (userFeedback.getId() == null) {
            userFeedbackRepository.persist(userFeedback);
        } else {
            userFeedbackRepository.merge(userFeedback);
        }

        status.setComplete();

        return "Tacksida";
    }

    private String lookupCaseContact(UserFeedback userFeedback, Long formTemplateId) {
        FormTemplate template = formTemplateRepository.find(formTemplateId);
        CustomCategory customCategory = template.getCustomCategory();

        System.out.println(customCategory.getName());

        // 0: Check if there exist a customCategory
        if (customCategory == null) {
            return "";
        }
        // 1: check if customCategory
        if (customCategory.getName().equals(userFeedback.getCaseCategory())) {
            List<String> caseSubCategoryList = userFeedback.getCaseSubCategory();
            // 2: check if single selection - else defaultContact
            if (caseSubCategoryList == null || caseSubCategoryList.size() != 1) {
                return customCategory.getDefaultContact();
            }

            String caseSubCategory = caseSubCategoryList.get(0);
            // 3: check if customSubCategory has contact
            for (CustomSubCategory subCategory : customCategory.getCustomSubCategories()) {
                if (subCategory.getName().equals(caseSubCategory)) {
                    if (!StringUtils.isBlank(subCategory.getContact())) {
                        return subCategory.getContact();
                    }
                }
            }
        }
        return customCategory.getDefaultContact();
    }

    private void processAttachment(MultipartFile file, UserFeedback userFeedback) {
        if (file.isEmpty()) return;

        Collection<Attachment> attachments = userFeedback.getAttachments();
        try {
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setFile(file.getBytes());

            System.out.println(file.getOriginalFilename());

//            attachmentRepository.persist(attachment);

            attachments.add(attachment);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
