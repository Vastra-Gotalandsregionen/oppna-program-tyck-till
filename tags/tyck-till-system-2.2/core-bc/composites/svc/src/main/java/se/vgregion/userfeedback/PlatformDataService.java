package se.vgregion.userfeedback;

import javax.servlet.http.HttpServletRequest;

import se.vgregion.userfeedback.domain.PlatformData;

public interface PlatformDataService {

    /**
     * Map header data in an HTTP request to a corresponding {@code PlatformData} object.
     * 
     * @param request
     *            Http request.
     * @return .
     */
    PlatformData mapUserPlatform(HttpServletRequest request);
}
