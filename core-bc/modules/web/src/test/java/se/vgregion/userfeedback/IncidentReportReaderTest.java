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

package se.vgregion.userfeedback;

import java.io.StringBufferInputStream;

import junit.framework.TestCase;

import org.junit.Test;

public class IncidentReportReaderTest extends TestCase {

    private String incidentReport1 =
            "<incidentReport>" + "<reportType>error</reportType>" + "<errorType>errorMessage</errorType>"
                    + "<timestamp>2008-08-08 10:22:22:222</timestamp>" + "<browser>Mozilla</browser>"
                    + "<defaultErrorMessage>defaultErrorMessage</defaultErrorMessage>"
                    + "<description>description</description>" + "<feedback>"
                    + "	<sendFeedback>true</sendFeedback>" + " 	<feedbackBySms activated=\"false\">"
                    + "		<phoneNumber>070-666666</phoneNumber>" + "	</feedbackBySms>"
                    + "	<feedbackByPhone activated=\"false\">" + "		<phoneNumber>070-555555</phoneNumber>"
                    + "	</feedbackByPhone>" + "	<feedbackByMail activated=\"true\">"
                    + "  		<email>john.doe@domain.com</email>" + "	</feedbackByMail>" + "</feedback>"
                    + "<screenShot>" + "	<sendScreenShot>true</sendScreenShot>" + "	<files>"
                    + "		<file filename=\"\" mediatype=\"\"/>" + "	</files>" + "</screenShot>	"
                    + "</incidentReport>";

    private String incidentReport2 =
            "<incidentReport>"
                    + "<reportType>error</reportType>"
                    + "<errorType>errorMessage</errorType>"
                    + "<timestamp>2008-08-08 10:22:22:222</timestamp>"
                    + "<browser>Mozilla</browser>"
                    + "<defaultErrorMessage>defaultErrorMessage</defaultErrorMessage>"
                    + "<description>description</description>"
                    + "<feedback>"
                    + "	<sendFeedback>true</sendFeedback>"
                    + " 	<feedbackBySms activated=\"true\">"
                    + "		<phoneNumber>070-666666</phoneNumber>"
                    + "	</feedbackBySms>"
                    + "	<feedbackByPhone activated=\"true\">"
                    + "		<phoneNumber>070-555555</phoneNumber>"
                    + "	</feedbackByPhone>"
                    + "	<feedbackByMail activated=\"false\">"
                    + "  		<email>john.doe@domain.com</email>"
                    + "	</feedbackByMail>"
                    + "</feedback>"
                    + "<screenShot>"
                    + "	<sendScreenShot>true</sendScreenShot>"
                    + "	<files>"
                    + "		    <file filename=\"DSC00059.JPG\" mediatype=\"image/jpeg\">file:/tmp/xforms_upload_24990.tmp</file>"
                    + "			<file filename=\"DSC00060.JPG\" mediatype=\"image/jpeg\">file:/tmp/xforms_upload_24991.tmp</file>"
                    + "	</files>" + "</screenShot>	" + "</incidentReport>";

    @SuppressWarnings("deprecation")
    @Test
    public void testReadBasicIncidentReport() throws Exception {
        IncidentReportReader reader = new IncidentReportReader();
        IncidentReport ir = reader.parseIncidentReport(new StringBufferInputStream(incidentReport1));
        assertNotNull(ir);

        assertEquals("Detta fungerar inte", ir.getReportType());
        assertEquals(1, ir.getErrorTypes().size());
        assertEquals("Felmeddelande", ir.getErrorTypes().get(0));
        // assertTrue(ir.getBrowser().indexOf("Mozilla") > -1);
        assertEquals("defaultErrorMessage", ir.getDefaultErrorMessage());
        assertEquals("description", ir.getDescription());
        assertTrue(ir.isSendFeedback());
        assertTrue(ir.isFeedbackByMail());
        assertEquals("john.doe@domain.com", ir.getEmailAddress());
        assertFalse(ir.isFeedbackByPhone());
        assertEquals(null, ir.getPhoneNumber());
        assertFalse(ir.isFeedbackBySms());
        assertEquals(null, ir.getSmsPhoneNumber());
        assertTrue(ir.isSendScreenShot());
        assertEquals(0, ir.getScreenShots().size());

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testReadIncidentReportWithFiles() throws Exception {
        IncidentReportReader reader = new IncidentReportReader();
        IncidentReport ir = reader.parseIncidentReport(new StringBufferInputStream(incidentReport2));
        assertNotNull(ir);

        assertEquals("Detta fungerar inte", ir.getReportType());
        assertEquals(1, ir.getErrorTypes().size());
        assertEquals("Felmeddelande", ir.getErrorTypes().get(0));
        // assertEquals("Mozilla", ir.getBrowser());
        assertEquals("defaultErrorMessage", ir.getDefaultErrorMessage());
        assertEquals("description", ir.getDescription());
        assertTrue(ir.isSendFeedback());
        assertFalse(ir.isFeedbackByMail());
        assertEquals(null, ir.getEmailAddress());
        assertTrue(ir.isFeedbackByPhone());
        assertEquals("070-555555", ir.getPhoneNumber());
        assertTrue(ir.isFeedbackBySms());
        assertEquals("070-666666", ir.getSmsPhoneNumber());
        assertTrue(ir.isSendScreenShot());
        assertEquals(2, ir.getScreenShots().size());

    }

}
