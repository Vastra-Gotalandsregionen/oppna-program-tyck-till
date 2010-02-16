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
