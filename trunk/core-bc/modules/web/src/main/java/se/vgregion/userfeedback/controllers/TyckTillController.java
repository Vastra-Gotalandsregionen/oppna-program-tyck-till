package se.vgregion.userfeedback.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import se.vgregion.userfeedback.FeedbackReportService;
import se.vgregion.userfeedback.PlatformDataService;
import se.vgregion.userfeedback.domain.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@RequestMapping(value = { "/KontaktaOss" })
@SessionAttributes("userFeedback")
public class TyckTillController {
    private static final Logger logger = LoggerFactory.getLogger(TyckTillController.class);

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private StaticCategoryRepository staticCategoryRepository;

    @Autowired
    private FeedbackReportService reportService;

    @Autowired
    private PlatformDataService platformDataService;

    @Value("${deploy.path}")
    private String deployPath;


    // @Autowired
    // private FeedbackReportService reportService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(CustomSubCategory.class, new CustomSubCategoryPropertyEditor());
    }

    /**
     * Used to render the userFeedback form. Basic initialization before render-view.
     * 
     * @param formName
     *            - FormTemplate name. The request parameter "formName" decide how the form is rendered. It is an
     *            inparameter to give the client this choise.
     * @param breadcrumb
     *            - client specific inparameter declaring from where the contact were initiated.
     * @param model
     *            - the normal Spring ModelMap.
     * @return - view to be rendered.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "formName", required = false) String formName, @RequestParam(
            value = "breadcrumb", required = false) String breadcrumb, HttpServletRequest request, ModelMap model) {

        FormTemplate template = lookupFormTemplate(formName);
        model.addAttribute("template", template);

        model.addAttribute("contentCategory", staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_CONTENT_CATEGORY));
        model.addAttribute("functionCategory", staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_FUNCTION_CATEGORY));
        model.addAttribute("otherCategory", staticCategoryRepository
                .find(StaticCategoryRepository.STATIC_OTHER_CATEGORY));

        UserFeedback userFeedback;
        if (!model.containsKey("userFeedback")) {
            userFeedback = new UserFeedback();
            userFeedback.setCaseCategory(null);
            model.addAttribute("userFeedback", userFeedback);
        } else {
            userFeedback = (UserFeedback) model.get("userFeedback");
        }
        userFeedback.setBreadcrumb(breadcrumb);

        userFeedback.setPlatformData(platformDataService.mapUserPlatform(request));

        model.addAttribute("contactOptions", UserContact.UserContactOption.getLabelMap());

        model.addAttribute("deployPath", deployPath);

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

    /**
     * Action method to send Userfeedback.
     * 
     * @param userFeedback
     *            - form backing bean.
     * @param formTemplateId
     *            - FormTemplate used to render form, used for extracting CustomCategory configuration.
     * @param multipartRequest
     *            - the http-request used for attachment upload and PlatformData extraction.
     * @param status
     *            - controls Session state.
     * @param model
     *            - the normal Spring ModelMap.
     * @return - view to render.
     */
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public String sendUserFeedback(@ModelAttribute("userFeedback") UserFeedback userFeedback,
                                   @RequestParam("formTemplateId") Long formTemplateId, MultipartHttpServletRequest multipartRequest,
                                   SessionStatus status, ModelMap model) {
        logger.info("Sending...");

        // logger.debug("User agent data captured: " + report);

        processUserfeedback(userFeedback, formTemplateId, multipartRequest);
        reportService.reportFeedback(userFeedback);

        log(userFeedback, model);

        // Log UserFeedback in db
        if (userFeedback.getId() == null) {
            userFeedbackRepository.persist(userFeedback);
        } else {
            userFeedbackRepository.merge(userFeedback);
        }

        status.setComplete();

        model.addAttribute("deployPath", deployPath);

        return "redirect:"+userFeedback.getPlatformData().getReferer();
    }

    private void processUserfeedback(UserFeedback userFeedback, Long formTemplateId,
                                     MultipartHttpServletRequest multipartRequest) {
        // 1: Lookup Attachments
        for (Iterator<String> filenameIterator = multipartRequest.getFileNames(); filenameIterator.hasNext();) {
            String fileName = filenameIterator.next();

            processAttachment(multipartRequest.getFile(fileName), userFeedback);
        }

        FormTemplate template = formTemplateRepository.find(formTemplateId);
        // 2: Lookup CaseCategory
        String caseCategory = lookupCaseCategory(userFeedback, template);
        userFeedback.setCaseCategory(caseCategory);

        // 3: Lookup CaseSubCategory
        List<String> caseSubCategories = lookupCaseSubCategory(userFeedback, template);
        userFeedback.setCaseSubCategories(caseSubCategories);

        // 4: Lookup CaseContact
        Backend backend = lookupCaseBackend(userFeedback, template);
        userFeedback.setCaseBackend(backend);
    }

    private String lookupCaseCategory(UserFeedback userFeedback, FormTemplate template) {
        if (userFeedback.getCaseCategoryId() == null) {
            return "";
        }

        if (userFeedback.getCaseCategoryId() > 0) {
            return template.getCustomCategory().getName();
        }

        return staticCategoryRepository.find(userFeedback.getCaseCategoryId()).getName();
    }

    private List<String> lookupCaseSubCategory(UserFeedback userFeedback, FormTemplate template) {
        List<String> subCategories = new ArrayList<String>();
        if (userFeedback.getCaseSubCategoryIds() == null) {
            return subCategories;
        }

        if (userFeedback.getCaseCategoryId() > 0) {
            CustomCategory customCategory = template.getCustomCategory();
            for (Long subCategoryId : userFeedback.getCaseSubCategoryIds()) {
                for (CustomSubCategory subCategory : customCategory.getCustomSubCategories()) {
                    if (subCategoryId.equals(subCategory.getId())) {
                        subCategories.add(subCategory.getName());
                    }
                }
            }

            return subCategories;
        }

        StaticCategory category = staticCategoryRepository.find(userFeedback.getCaseCategoryId());
        for (Long subCategoryId : userFeedback.getCaseSubCategoryIds()) {
            for (Map.Entry<Long, String> subCategoryEntry : category.getSubCategories().entrySet()) {
                if (subCategoryId.equals(subCategoryEntry.getKey())) {
                    subCategories.add(subCategoryEntry.getValue());
                }
            }
        }

        return subCategories;
    }

    private Backend lookupCaseBackend(UserFeedback userFeedback, FormTemplate template) {
        Backend backend = null;

        // No category selected
        if (userFeedback.getCaseCategoryId() == null) {
            return getFormTemplateBackend(template);
        }

        // CustomCategory selected
        if (userFeedback.getCaseCategoryId() > 0) {
            CustomCategory category = template.getCustomCategory();

            // No subCategory or subcategory not unique
            List<Long> subCategoryIds = userFeedback.getCaseSubCategoryIds();
            if (subCategoryIds.size() == 0 || subCategoryIds.size() > 1) {
                return getCustomCategoryBackend(category, template);
            }

            CustomSubCategory subCategory = null;
            Long subCategoryId = subCategoryIds.get(0);
            for (CustomSubCategory sub : category.getCustomSubCategories()) {
                if (subCategoryId.equals(sub.getId())) {
                    subCategory = sub;
                }
            }
            if (subCategory == null || subCategory.getBackend().isBlank()) {
                return getCustomCategoryBackend(category, template);
            }

            return subCategory.getBackend();
        }

        // Static Category selected
        // Content
        if (userFeedback.getCaseCategoryId().equals(StaticCategoryRepository.STATIC_CONTENT_CATEGORY)) {
            if (template.getContentBackend() == null || template.getContentBackend().isBlank()) {
                return getFormTemplateBackend(template);
            }
            return template.getContentBackend();
        }
        // Function
        if (userFeedback.getCaseCategoryId().equals(StaticCategoryRepository.STATIC_FUNCTION_CATEGORY)) {
            if (template.getFunctionBackend() == null || template.getFunctionBackend().isBlank()) {
                return getFormTemplateBackend(template);
            }
            return template.getFunctionBackend();
        }
        // Other
        if (userFeedback.getCaseCategoryId().equals(StaticCategoryRepository.STATIC_OTHER_CATEGORY)) {
            if (template.getOtherBackend() == null || template.getOtherBackend().isBlank()) {
                return getFormTemplateBackend(template);
            }
            return template.getOtherBackend();
        }

        return new Backend();
    }

    private Backend getFormTemplateBackend(FormTemplate template) {
        if (template.getDefaultBackend() == null) {
            return new Backend();
        }
        return template.getDefaultBackend();
    }

    private Backend getCustomCategoryBackend(CustomCategory category, FormTemplate template) {
        if (category.getBackend() == null || category.getBackend().isBlank()) {
            return getFormTemplateBackend(template);
        }
        return category.getBackend();
    }

    private void processAttachment(MultipartFile file, UserFeedback userFeedback) {
        if (file.isEmpty()) {
            return;
        }

        Collection<Attachment> attachments = userFeedback.getAttachments();
        try {
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setFile(file.getBytes());

            logger.debug(file.getOriginalFilename());

            attachments.add(attachment);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    private void log(UserFeedback userFeedback, ModelMap model) {
        if (!logger.isDebugEnabled()) {
            return;
        }

        logger.debug("attachmentSize: " + userFeedback.getAttachments().size());

        for (Map.Entry<String, Object> entry : model.entrySet()) {
            logger.debug("Entry: " + entry.getKey());
        }
    }

    public FeedbackReportService getReportService() {
        return reportService;
    }

    public void setReportService(FeedbackReportService reportService) {
        this.reportService = reportService;
    }
}
