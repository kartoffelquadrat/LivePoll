package eu.kartoffelquadrat.livepoll.accesscontrol;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class to verify source (IP) of an API / WebUI caller. Eventually this should be replaced
 * by a Spring security filter chain.
 */
public class SourceChecker {

  /**
   * Helper method to determine if a servlet connection was established from the same machine as the
   * server is running on.
   *
   * @param request as the Http servlet request to examine.
   * @return true if the request origin is localhost, false otherwise.
   */
  public static boolean isCallFromLocalhostLanOrVpn(HttpServletRequest request) {

    System.out.println(request.getRemoteAddr());

    boolean isFromLocalHost = request.getRemoteAddr().equals("127.0.0.1");
    boolean isFromLan = request.getRemoteAddr().startsWith("192.168");
    // IP of the VPN access point (public IP of router) TODO: READ FROM CONFIG FILE!
    boolean isFromVpn = request.getRemoteAddr().equals("24.202.66.103");
    return isFromLocalHost || isFromLan || isFromVpn;
  }

}