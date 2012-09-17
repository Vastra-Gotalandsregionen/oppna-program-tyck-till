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

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.vgregion.incidentreport.domain.UserFeedback;
import se.vgregion.incidentreport.domain.UserOpinion;

/**
 * Spring portlet controller class.
 * 
 * @author David Bennehult & Ulf Carlsson.
 * 
 */
@Controller
@RequestMapping("VIEW")
public class TyckTillViewController {

    /**
     * Name of the view page.
     */
    public static final String USER_OPINION_VIEW = "tyckTill";
    public static final String USER_FEEDBACK_VIEW = "kontaktaOss";

    @Autowired
    private PortletConfig portletConfig;

    public void setPortletConfig(PortletConfig portletConfig) {
        this.portletConfig = portletConfig;
    }

    /**
     * Set values of and return a formBackingObject for a UserFeedback ("Kontakta oss")
     * 
     * @param preferences
     *            PortletPreferences
     * @return RaindanceSettings object with values saved in preferences.
     */
    @ModelAttribute("userFeedback")
    public UserFeedback makeNewFeedbackForm() {
        UserFeedback userFeedback = new UserFeedback();
        // userFeedback.setTestAttribute(preferences.getValue("MY_VALUE", "0"));
        return userFeedback;
    }

    /**
     * Set values of and return a formBackingObject for a UserOpinion ("Tyck till") form
     * 
     * @return
     */
    @ModelAttribute("userOpinion")
    public UserOpinion makenewOpinionForm() {
        UserOpinion userOpinion = new UserOpinion();
        return userOpinion;
    }

    /**
     * Render method for Raindance notifier portlet.
     * 
     * @param model
     *            Portlet model.
     * @param renderRequest
     *            Portlet request.
     * @param renderResponse
     *            Portlet response.
     * @return String presentation of the portlet view page.
     */
    @RenderMapping
    public final String showTyckTill(ModelMap model, RenderRequest renderRequest, RenderResponse renderResponse) {
        Map<String, ?> userInfo = (Map<String, ?>) renderRequest.getAttribute(PortletRequest.USER_INFO);
        String userId = "";
        if (userInfo != null) {
            userId = (String) userInfo.get(PortletRequest.P3PUserInfos.USER_LOGIN_ID.toString());
        }

        PortletPreferences preferences = renderRequest.getPreferences();
        String listSizeLimit = preferences.getValue("MyValue", "5");

        // Trim down size of list if necessary.
        int listSize = Integer.valueOf(listSizeLimit);

        ResourceBundle bundle = portletConfig.getResourceBundle(renderResponse.getLocale());
        if (bundle != null) {
            // renderResponse.setTitle(bundle.getString("My String"));

        }

        // model.addAttribute("urgentSpan", Integer.parseInt(urgentSpan));
        model.addAttribute("mainHeading", "Kontakta oss");
        model.addAttribute("leadText", "Här kan du lämna synpunkter");
        model.addAttribute("caseSubjects", UserFeedback.CaseSubject.values());
        return USER_FEEDBACK_VIEW;
    }

    @ActionMapping
    public void saveFeedbackForm(ModelMap model) {

    }
}
