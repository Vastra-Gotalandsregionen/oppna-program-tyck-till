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

import java.util.Properties;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.vgr.incidentreport.IncidentReport;
import se.vgr.incidentreport.IncidentReportService;
import se.vgr.usdservice.USDService;
import se.vgr.util.EMailClient;

@Service("incidentReportService")
public class IncidentReportServiceImpl implements IncidentReportService {

    private static final String INCIDENT_REPORT_SERVICE_ADMIN_EMAIL = "culberto@hotmail.com";
    private static final String INCIDENT_REPORT_SERVICE_NOREPLY = "IncidentReportService-noreply@vgregion.se";
    private static final String INCIDENT_REPORT_EMAIL_SUBJECT = "IncidentReportService Error";
    @Autowired
    private USDService usdService;

    public int reportIncident(IncidentReport ir) {

        int result = 0;

        try {

            String method = ir.getReportMethod();

            if ("usd".equalsIgnoreCase(method)) {
                try {
                    Properties parameters = mapToRequestParameters(ir);
                    usdService.createRequest(parameters, ir.getUserId());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    sendReportByEmail(ir);
                }
            }
            else {
                sendReportByEmail(ir);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    private void sendReportByEmail(IncidentReport ir) throws MessagingException {
        String reportEmail = ir.getReportEmail();

        if (reportEmail != null && !"".equals(reportEmail)) {
            String body = "";
            String[] reportEmailArray = { reportEmail };
            body += ir.toString();
            try {
                new EMailClient().postMail(reportEmailArray, INCIDENT_REPORT_EMAIL_SUBJECT, "" + body,
                        INCIDENT_REPORT_SERVICE_NOREPLY);
            }
            catch (Throwable e1) {

                e1.printStackTrace();
                String[] emailTo = { INCIDENT_REPORT_SERVICE_ADMIN_EMAIL };

                new EMailClient().postMail(emailTo, INCIDENT_REPORT_EMAIL_SUBJECT, "" + e1.getMessage() + "\n"
                        + body, INCIDENT_REPORT_SERVICE_NOREPLY);

            }

        }
    }

    private Properties mapToRequestParameters(IncidentReport ir) {
        Properties p = new Properties();
        p.setProperty("affected_resource", "nr:BF5880E3AF1C8542B2546B93922C25A7");
        p.setProperty("category", "pcat:400023");

        p.setProperty("group", "cnt:A455761E38B4B8488B5F0999BE5A4637");
        p.setProperty("impact", "imp:1603");
        p.setProperty("priority", "pri:500");
        p.setProperty("type", "crt:182");
        p.setProperty("urgency", "urg:1100");
        p.setProperty("z_location", "loc:67F817D782E87B45A8298FC5512B6A9C");
        p.setProperty("z_organization", "org:5527E3F8D19F49409036F162493C7DD0");
        p.setProperty("z_telefon_nr", ir.getPhoneNumber() != null ? ir.getPhoneNumber() : "N/A");
        StringBuffer descBuf = new StringBuffer();
        descBuf.append("User agent:" + ir.getBrowser() + "\n");
        descBuf.append("Beskrivning:" + ir.getDescription() + "\n");
        p.setProperty("description", descBuf.toString());

        return p;
    }

}
