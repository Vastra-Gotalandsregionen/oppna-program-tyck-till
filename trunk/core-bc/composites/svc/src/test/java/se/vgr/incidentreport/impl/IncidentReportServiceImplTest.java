package se.vgr.incidentreport.impl;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.vgr.incidentreport.IncidentReport;

public class IncidentReportServiceImplTest extends TestCase {
    private static final Log log = LogFactory.getLog(IncidentReportServiceImplTest.class);
    private IncidentReportServiceImpl reportService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        reportService = new IncidentReportServiceImpl();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testReportIncident() {
        IncidentReport ir = new IncidentReport();
        // TODO
        // reportService.reportIncident(ir);
    }
}
