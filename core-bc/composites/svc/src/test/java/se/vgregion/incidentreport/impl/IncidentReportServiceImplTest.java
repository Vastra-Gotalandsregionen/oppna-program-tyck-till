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

package se.vgregion.incidentreport.impl;

import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import se.vgregion.incidentreport.IncidentReport;
import se.vgregion.incidentreport.Screenshot;
import se.vgregion.incidentreport.impl.IncidentReportServiceImpl;
import se.vgregion.incidentreport.pivotaltracker.PTStory;
import se.vgregion.incidentreport.pivotaltracker.PivotalTrackerService;
import se.vgregion.usdservice.USDService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IncidentReportServiceImpl.class)
public class IncidentReportServiceImplTest {
    private IncidentReportServiceImpl reportService;

    @Before
    public void setUp() throws Exception {
        reportService = new IncidentReportServiceImpl();
    }

    @Test
    public void testCreateStoryException() throws IOException {
        File tempFile = File.createTempFile("incidentReportTest", "test");
        IncidentReport ir = new IncidentReport();
        ir.setReportMethod("usd");
        Screenshot screenshot = new Screenshot();
        screenshot.setPath(tempFile.getAbsolutePath());
        screenshot.setFileName(tempFile.getName());
        ir.addScreenShot(screenshot);

        String appName = "Tyck_till_test_portlet";
        ir.setReportMethod("pivotaltracker");
        ir.setUserId("testUser");
        ir.setApplicationName(appName);
        PivotalTrackerService pivotalTrackerClientMock = mock(PivotalTrackerService.class);
        Properties pivotalTrackerMappingsMock = mock(Properties.class);
        ReflectionTestUtils.setField(reportService, "pivotalTrackerClient", pivotalTrackerClientMock,
                PivotalTrackerService.class);
        ReflectionTestUtils.setField(reportService, "pivotalTrackerMappings", pivotalTrackerMappingsMock,
                Properties.class);
        Mockito.when(pivotalTrackerClientMock.createuserStory(any(PTStory.class))).thenReturn("http://fakeURL");
        doThrow(new RuntimeException()).when(pivotalTrackerClientMock).addAttachmentToStory(anyString(),
                any(PTStory.class));
        ir.setReportEmail("culberto@hotmail.com");
        reportService.reportIncident(ir);

        ReflectionTestUtils.setField(ir, "screenShots", null, List.class);
        reportService.reportIncident(ir);
    }

    @Test
    public void testReportIncident() throws Exception {
        File tempFile = File.createTempFile("incidentReportTest", "test");

        String appName = "Tyck_till_test_portlet";
        IncidentReport ir = new IncidentReport();
        ir.setUserId("testUser");
        ir.setApplicationName(appName);

        // ==================================================================
        // Test USD reporting
        ir.setReportMethod("usd");
        Screenshot screenshot = new Screenshot();
        screenshot.setPath(tempFile.getAbsolutePath());
        screenshot.setFileName(tempFile.getName());
        ir.addScreenShot(screenshot);
        USDService usdServiceMock = mock(USDService.class);
        when(usdServiceMock.getUSDGroupHandleForApplicationName(appName)).thenReturn("01 Gbg.SC.Service Desk IT");

        // Set autowired usdService property
        ReflectionTestUtils.setField(reportService, "usdService", usdServiceMock, USDService.class);

        reportService.reportIncident(ir);
        Mockito.verify(usdServiceMock).createRequest(any(Properties.class), anyString(), anyListOf(File.class),
                anyListOf(String.class));

        // test the method when an exception is thrown
        ir.setApplicationName(null);
        ir.setReportEmail("culberto@hotmail.com");
        reportService.reportIncident(ir);

        // need to set a proper application name so that the rest of the tests run
        ir.setApplicationName(appName);

        // ==================================================================
        // Test Pivotal Tracker reporting
        ir.setReportMethod("pivotaltracker");
        PivotalTrackerService pivotalTrackerClientMock = mock(PivotalTrackerService.class);
        Properties pivotalTrackerMappingsMock = mock(Properties.class);
        ReflectionTestUtils.setField(reportService, "pivotalTrackerClient", pivotalTrackerClientMock,
                PivotalTrackerService.class);
        ReflectionTestUtils.setField(reportService, "pivotalTrackerMappings", pivotalTrackerMappingsMock,
                Properties.class);

        when(pivotalTrackerMappingsMock.getProperty(appName)).thenReturn("35420");
        when(pivotalTrackerClientMock.createuserStory(any(PTStory.class))).thenReturn("http://fakeURL");
        reportService.reportIncident(ir);

        // same as above, test the method when an exception is thrown
        ir.setApplicationName(null);
        reportService.reportIncident(ir);
        // need to set a proper application name so that the rest of the tests run
        ir.setApplicationName(appName);
        // ==================================================================

        // ==================================================================
        // Test when no method is specified, should indicate email report
        ir.setReportMethod("");
        reportService.reportIncident(ir);
        verifyPrivate(reportService).invoke("sendReportByEmail", ir, "*ERROR in IncidentReportService* ");

        // test the method when an exception is thrown
        ir.setApplicationName(null);
        reportService.reportIncident(ir);
        // need to set a proper application name so that the rest of the tests run
        ir.setApplicationName(appName);
        // ==================================================================

        // ==================================================================
        // finally, cause an error at the top of the reportIncident method
        ir = null;
        reportService.reportIncident(ir);
        // ==================================================================
    }

}
