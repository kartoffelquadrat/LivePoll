package eu.kartoffelquadrat.livepoll.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebControllers {

  @RequestMapping("/")
  public String forwardToLanding(HttpServletRequest request) {

    if (isCallFromLocalhost(request))
      return "setup";
    else
      return "denied";
  }

  /**
   * Helper method to determine if a servlet connection was established from the same machine as the
   * server is running on.
   *
   * @param request as the Http servlet request to examine.
   * @return true if the request origin is localhost, false otherwise.
   */
  private boolean isCallFromLocalhost(HttpServletRequest request) {
    return request.getRemoteAddr().equals("127.0.0.1");
  }
}
