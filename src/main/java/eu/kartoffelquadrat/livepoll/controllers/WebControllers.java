package eu.kartoffelquadrat.livepoll.controllers;

import eu.kartoffelquadrat.livepoll.Poll;
import eu.kartoffelquadrat.livepoll.PollLauncher;
import eu.kartoffelquadrat.livepoll.PollManager;
import eu.kartoffelquadrat.livepoll.pollutils.AlphabetSanitizer;
import eu.kartoffelquadrat.livepoll.pollutils.Hyphenizer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebControllers {

  @Autowired
  PollManager pollManager;

  @RequestMapping("/")
  public String forwardToLanding(HttpServletRequest request) {

    if (!isCallFromLocalhost(request)) {
      return "denied";
    } else {
      return "setup";
    }
  }

  @RequestMapping("/polls/{pollid}")
  public String accessPoll(@PathVariable("pollid") String pollid, Model model,
                           HttpServletRequest request) {

    if (!isCallFromLocalhost(request)) {
      return "denied";
    } else {
      if (pollManager.isExistentPoll(pollid)) {

        // Store information required to render in model, so that thymeleaf can insert it (server sided)
        model.addAttribute("bufferdir", PollLauncher.pollTmpDir);
        model.addAttribute("pollid", pollid);

        Poll poll = pollManager.getPollByIdentifier(pollid);
        model.addAttribute("topic", poll.getTopic());

        // fill available options depending on poll characteristics
        model.addAttribute("firstoptiontext", poll.getOptions()[0]);
        model.addAttribute("firstoptioncode",
            Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(poll.getOptions()[0])));
        int lastOptionIndex = poll.getOptions().length - 1;
        model.addAttribute("lastoptiontext", poll.getOptions()[lastOptionIndex]);
        model.addAttribute("lastoptioncode",
            Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(poll.getOptions()[lastOptionIndex])));

        if (poll.getOptions().length == 2) {
          model.addAttribute("maybeoptiontext", "");
          model.addAttribute("maybeoptioncode", "");
        } else if (poll.getOptions().length == 3) {
          model.addAttribute("maybeoptiontext", poll.getOptions()[1]);
          model.addAttribute("maybeoptioncode",
              Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(poll.getOptions()[1])));
        } else {
          throw new RuntimeException("Only binary / tertiary polls supported for now.");
        }

        return "poll";
      } else {
        return "redirect:/";
      }
    }
  }

  /**
   * An actual endpoint, referenced by generated QR code. Note using Get operation here is a clear
   * violation to the REST style, but since we want to support vote by QR scanning it has to be GET
   * (default HTTP method for browser resource access).
   */
  @RequestMapping("/polls/{pollid}/{option}")
  public String registerVote(@PathVariable("pollId") String pollId,
                             @PathVariable("option") String option) {

    pollManager.getPollByIdentifier(pollId).voteForOption(option);
    return "I registered your vote for \"" + option +
        "\". Thank you for your participation. You can leave this page now.";
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
