package se.vgregion.userfeedback.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import se.vgregion.userfeedback.PlatformDataService;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.util.UserAgentUtils;

public class PlatformDataServiceImpl implements PlatformDataService {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String REFERER_HEADER = "Referer";

    /**
     * Map header data in an HTTP request to a corresponding {@code PlatformData} object.
     * 
     * @param request
     *            Http request.
     * @return .
     */
    @Override
    public PlatformData mapUserPlatform(HttpServletRequest request) {
        PlatformData platform = new PlatformData();
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        String referer = request.getHeader(REFERER_HEADER);
        Date timeStamp = new Date(); // Capture current time, not User's time.

        platform.setBrowser(UserAgentUtils.getBrowser(userAgent));
        platform.setOperatingSystem(UserAgentUtils.getOS(userAgent));
        platform.setTimeStamp(timeStamp);
        platform.setReferer(referer);
        platform.setIpAddress(request.getRemoteAddr());
        platform.setUserId(request.getRemoteUser());

        return platform;
    }
}
