package se.vgregion.userfeedback.impl;

import org.apache.commons.lang.StringUtils;
import se.vgregion.userfeedback.PlatformDataService;
import se.vgregion.userfeedback.domain.PlatformData;
import se.vgregion.util.UserAgentUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * Implementation of the PlatformDataService.
 */
public class PlatformDataServiceImpl implements PlatformDataService {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String REFERER_HEADER = "Referer";
    public static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * Map header data in an HTTP request to a corresponding {@code PlatformData} object.
     *
     * @param request Http request.
     * @return a PlatformData object.
     */
    @Override
    public PlatformData mapUserPlatform(HttpServletRequest request, String userId) {
        PlatformData platform = new PlatformData();
        String userAgent = request.getHeader(USER_AGENT_HEADER);
        String referer = request.getHeader(REFERER_HEADER);
        String forwardedIpAddress = getRemoteIpAddress(request);
        Date timeStamp = new Date(); // Capture current time, not User's time.

        if (userAgent != null) {
            platform.setBrowser(UserAgentUtils.getBrowser(userAgent));
            platform.setOperatingSystem(UserAgentUtils.getOS(userAgent));
        }
        platform.setTimeStamp(timeStamp);
        platform.setReferer(referer);
        platform.setIpAddress(request.getRemoteAddr());
        platform.setForwardedIpAddress(forwardedIpAddress);

        String remoteUser = request.getRemoteUser();
        if (StringUtils.isBlank(remoteUser)) {
            platform.setUserId(userId);
        } else {
            platform.setUserId(request.getRemoteUser());
        }

        return platform;
    }

    /**
     * Fix for Siteminder. Retrieves the original ip address of the user request if it has been stored in a
     * {@code HEADER_X_FORWARDED_FOR} header.
     *
     * @param request .
     * @return
     */
    private static String getRemoteIpAddress(HttpServletRequest request) {
        StringBuilder ipAddress = new StringBuilder();

        @SuppressWarnings("unchecked")
        Enumeration<String> forwardedForEnum = request.getHeaders(HEADER_X_FORWARDED_FOR);
        while (forwardedForEnum.hasMoreElements()) {
            ipAddress.append(forwardedForEnum.nextElement()).append(" ");
        }
        if (ipAddress.length() == 0) {
            ipAddress.append(request.getRemoteAddr()).append(" [Default]");
        }

        return ipAddress.toString().trim();
    }
}
