/**
 * Copyright 2009 Västra Götalandsregionen
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
package se.vgr.incidentreport.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import se.vgr.incidentreport.IncidentReport;
import se.vgr.incidentreport.IncidentReportService;
import se.vgr.incidentreport.Screenshot;
import se.vgr.usdservice.USDService;
import se.vgr.util.EMailClient;

@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

    /**
     * Reciever of messages when the regular report recievers can´t be reached.
     */
    private static final String INCIDENT_REPORT_SERVICE_ADMIN_EMAIL = "ulcan@wmdata.com";

    /**
     * Email reply address.
     */
    private static final String INCIDENT_REPORT_SERVICE_NOREPLY = "IncidentReportService-noreply@vgregion.se";

    /**
     * Email subject when something is not working as expected in Tyck till
     */
    private static final String INCIDENT_REPORT_ERROR_EMAIL_SUBJECT = "*ERROR in IncidentReportService* ";

    /**
     * Email subject
     */
    private static final String INCIDENT_REPORT_EMAIL_SUBJECT = "IncidentReportService message";

    private static final Log log = LogFactory.getLog(IncidentReportServiceImpl.class);

    @Autowired
    @Qualifier("usdService")
    private USDService usdService;

    public int reportIncident(IncidentReport ir) {

        int result = 0;

        try {

            String method = ir.getReportMethod();

            if ("usd".equalsIgnoreCase(method)) {
                try {
                    Properties parameters = mapToRequestParameters(ir);
                    List<Screenshot> ss = ir.getScreenShots();
                    List<File> files = new ArrayList<File>();
                    for (Screenshot s : ss) {
                        files.add(new File(s.getPath()));
                    }

                    usdService.createRequest(parameters, ir.getUserId(), files);
                }
                catch (Exception e) {
                    log.warn(e);
                    sendReportByEmail(ir, INCIDENT_REPORT_ERROR_EMAIL_SUBJECT + ":" + e.getMessage());
                }
            }
            else {
                sendReportByEmail(ir, INCIDENT_REPORT_EMAIL_SUBJECT);
            }
        }
        catch (Exception e) {
            log.error(e);
            result = -1;
        }
        return result;
    }

    private void sendReportByEmail(IncidentReport ir, String subject) throws MessagingException {
        String reportEmail = ir.getReportEmail();

        if (reportEmail != null && !"".equals(reportEmail)) {
            String body = "";
            String[] reportEmailArray = { reportEmail };
            body += ir.toString().replaceAll(IncidentReport.NEWLINE, "\n</br>");
            try {
                new EMailClient().postMail(reportEmailArray, ir.getApplicationName() + ":" + subject, "" + body,
                        INCIDENT_REPORT_SERVICE_NOREPLY, ir.getScreenShots());
            }
            catch (Throwable e1) {

                log.warn(e1);
                String[] emailTo = { INCIDENT_REPORT_SERVICE_ADMIN_EMAIL };

                new EMailClient().postMail(emailTo, ir.getApplicationName() + ":"
                        + INCIDENT_REPORT_ERROR_EMAIL_SUBJECT, "" + e1.getMessage() + "\n" + body,
                        INCIDENT_REPORT_SERVICE_NOREPLY, ir.getScreenShots());

            }

        }
    }

    private Properties mapToRequestParameters(IncidentReport ir) {
        Properties p = new Properties();
        p.setProperty("affected_resource", "nr:BF5880E3AF1C8542B2546B93922C25A7");
        p.setProperty("category", "pcat:400023");
        // map to group using application name?
        String appName = ir.getApplicationName().replaceAll(" ", "_");
        System.out.println("getting group for appName=" + appName);
        String groupName = usdService.getUSDGroupHandleForApplicationName(appName);
        // p.setProperty("group", "cnt:A455761E38B4B8488B5F0999BE5A4637");
        p.setProperty("group", groupId);
        p.setProperty("impact", "imp:1603");
        p.setProperty("priority", "pri:500");
        p.setProperty("type", "crt:182");
        p.setProperty("urgency", "urg:1100");
        p.setProperty("z_location", "loc:67F817D782E87B45A8298FC5512B6A9C");
        p.setProperty("z_organization", "org:5527E3F8D19F49409036F162493C7DD0");
        p.setProperty("z_telefon_nr", ir.getPhoneNumber() != null ? ir.getPhoneNumber() : "N/A");

        StringBuffer descBuf = new StringBuffer();

        descBuf.append(ir.toString() + "\n");
        p.setProperty("description", descBuf.toString());

        return p;
    }

}
