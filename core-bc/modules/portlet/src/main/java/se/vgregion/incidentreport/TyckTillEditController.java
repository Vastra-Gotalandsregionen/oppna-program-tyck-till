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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import se.vgregion.incidentreport.domain.TyckTillSettings;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import java.io.IOException;

@Controller
@RequestMapping("EDIT")
public class TyckTillEditController {

    /**
     * PortletPreferences attribute key for list max size.
     */
    public static final String MY_TEST_VALUE = "myTestvalue";
    private static final String CONFIG_JSP = "tyckTillEdit";

    @Autowired
    private Validator validator;

    Logger logger = Logger.getLogger(TyckTillEditController.class);

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Set values for formBackingObject from the PortletPreferences.
     * 
     * @param preferences
     *            PortletPreferences
     * @return RaindanceSettings object with values saved in preferences.
     */
    @ModelAttribute("tyckTillSettings")
    public TyckTillSettings formBackingObject(PortletPreferences preferences) {
        TyckTillSettings settings = new TyckTillSettings();
        settings.setTestAttribute(preferences.getValue("MY_VALUE", "0"));
        return settings;
    }

    /**
     * Edit method for show preferences of notifier.
     * 
     * @param modelMap
     *            Spring ModelMap.
     * @param raindanceSettings
     *            Command bean.
     * @param bindingResult
     *            Spring bindingResult bean.
     * @return String of jsp page file.
     */
    @RenderMapping
    public final String showPreferences(ModelMap modelMap,
            @ModelAttribute("raindanceSettings") TyckTillSettings raindanceSettings, BindingResult bindingResult) {
        validator.validate(raindanceSettings, bindingResult);
        return CONFIG_JSP;
    }

    /**
     * Save portlet prefernces values to the PortletPreferences.
     * 
     * @param settings
     *            Command bean.
     * @param bindingResult
     *            Spring BindingResult bean.
     * @param preferences
     *            PortletPreferences to save portlet preferences to.
     * @throws ReadOnlyException
     *             Throws if value couldn't be saved to the PortletPreferences.
     */
    @ActionMapping
    public void savePreferences(@ModelAttribute("raindanceSettings") TyckTillSettings settings,
            BindingResult bindingResult, PortletPreferences preferences) throws ReadOnlyException {

        validator.validate(settings, bindingResult);
        if (!bindingResult.hasErrors()) {
            preferences.setValue(MY_TEST_VALUE, settings.getTestAttribute());
            try {
                preferences.store();
            } catch (ValidatorException e) {
                logger.error("Error when storing prefernces.", e);
            } catch (IOException e) {
                logger.error("Error when storing prefernces.", e);
            }
        }
    }
}
