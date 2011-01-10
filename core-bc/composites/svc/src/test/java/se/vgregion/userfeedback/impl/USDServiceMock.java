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
 */

package se.vgregion.userfeedback.impl;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import se.vgregion.usdservice.USDService;
import se.vgregion.usdservice.domain.Issue;
import se.vgregion.util.Attachment;

/**
 * Mock USD service. Allows inspection of the request parameters and attachments passed to a USDService.
 * 
 * @author robde1
 * 
 */
public class USDServiceMock implements USDService {

    private static final String TEST_USD_HANDLE = "Test_handle_for_USD";

    private Properties requestParameters = new Properties();
    private Collection<Attachment> attachments;

    public static String getTestUsdHandle() {
        return TEST_USD_HANDLE;
    }

    public Properties getRequestParameters() {
        return requestParameters;
    }

    public Collection<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public String createRequest(Properties requestParameters, String arg1, Collection<Attachment> attachments) {
        this.requestParameters.putAll(requestParameters);
        this.attachments = attachments;
        return "";
    }

    @Override
    public List<Issue> getRecordsForContact(String arg0, Integer arg1) {
        throw new UnsupportedOperationException("TODO: Implement this method");
    }

    /**
     * Returns a fixed value.
     * 
     * @inheritDoc
     */
    @Override
    public String getUSDGroupHandleForApplicationName(String arg0) {
        return TEST_USD_HANDLE;
    }
}
