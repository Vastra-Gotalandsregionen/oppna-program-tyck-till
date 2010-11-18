package se.vgregion.incidentreport.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import se.vgregion.incidentreport.domain.UserFeedback;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */

@Controller
@RequestMapping("/tyckTillForm")
@SessionAttributes("userFeedback")
public class TyckTillController {
    private String mainHeading = "Default main heading";
    private String leadText = "Default lead text";


    @RequestMapping(method = RequestMethod.GET)
    public void setupForm(@RequestParam(value="mainHeading", required = false) String mainHeading,
                          @RequestParam(value="leadText", required = false) String leadText,
                          ModelMap model) {
        if (mainHeading != null) {
            this.mainHeading = mainHeading;
        }
        if (leadText != null) {
            this.leadText = leadText;
        }

        model.addAttribute("mainHeading", this.mainHeading);
        model.addAttribute("leadText", this.leadText);
        model.addAttribute("userFeedback", new UserFeedback());

        model.addAttribute("caseSubjects_contentRelated", UserFeedback.CaseSubject.webpageContentRelated);
        model.addAttribute("caseSubjects_functionRelated", UserFeedback.CaseSubject.webpageFunctionRelated);
        model.addAttribute("healthCareSubHeadings", UserFeedback.HealthcareSubHeadings.getLabelMap().entrySet());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sendUserFeedback(@RequestParam(value = "userFeedback", required = false) UserFeedback userFeedback,
                                   ModelMap model) {
        System.out.println("Sending...");

        UserFeedback.CaseSubject caseSubject = userFeedback.getCaseSubject();
        if (caseSubject != null) {
            System.out.println("CaseSubject: " +caseSubject.getName());
        }

        System.out.println("Message: "+ userFeedback.getMessage());

        return "feedbackSent";
    }
}
