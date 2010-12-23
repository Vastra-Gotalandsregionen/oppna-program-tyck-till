package se.vgregion.userfeedback.impl;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import se.vgregion.usdservice.USDService;
import se.vgregion.usdservice.domain.Issue;
import se.vgregion.util.Attachment;

/**
 * Mock USD service.
 * 
 * @author robde1
 * 
 */
public class USDServiceMock implements USDService {

    private static final String TEST_USD_HANDLE = "Test_handle_for_USD";

    private Properties requestParameters;
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
        this.requestParameters = requestParameters;
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
