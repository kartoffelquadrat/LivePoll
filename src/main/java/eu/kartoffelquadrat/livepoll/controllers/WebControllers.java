package eu.kartoffelquadrat.livepoll.controllers;

import eu.kartoffelquadrat.livepoll.Poll;
import eu.kartoffelquadrat.livepoll.PollLauncher;
import eu.kartoffelquadrat.livepoll.PollManager;
import eu.kartoffelquadrat.livepoll.pollutils.AlphabetSanitizer;
import eu.kartoffelquadrat.livepoll.pollutils.Hyphenizer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  @GetMapping("/polls/{pollid}/options/{option}")
  public String registerVote(@PathVariable("pollid") String pollId,
                             @PathVariable("option") String option) {

    pollManager.getPollByIdentifier(pollId).voteForOption(option);
    return "I registered your vote for \"" + option +
        "\". Thank you for your participation. You can leave this page now.";
  }


  /**
   * Helper method to access QR code png files stored on disk. Local references are blockedc by
   * browsers security policy so we need an HTTP tunnel.
   *
   * @param pollid  as the id of the qr code option to look up
   * @param option  as the selected vote option
   * @param request as the connection http opeject, required to identify connects form other
   *                machines and reject them.
   * @return ByteStream of QR png with required Meta media type.
   * @throws IOException in case the file is not found / cannot be read.
   */
  @GetMapping(
      value = "/polls/{pollid}/qr/{option}",
      produces = MediaType.IMAGE_PNG_VALUE
  )
  public @ResponseBody
  byte[] getImageWithMediaType(@PathVariable("pollid") String pollid,
                               @PathVariable("option") String option, HttpServletRequest request)
      throws IOException {

    // dont accept calls from elsewhere
    if (!isCallFromLocalhost(request)) {
      return null;
    }

    // Return the referenced QR code
    FileInputStream in = new FileInputStream(
        PollLauncher.pollTmpDir +'/'+ pollid + "-" + option +
            ".png");
    return IOUtils.toByteArray(in);
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
