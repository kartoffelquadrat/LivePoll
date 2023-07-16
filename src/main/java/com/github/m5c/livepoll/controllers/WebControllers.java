package com.github.m5c.livepoll.controllers;

import com.github.m5c.livepoll.Poll;
import com.github.m5c.livepoll.PollLauncher;
import com.github.m5c.livepoll.PollManager;
import com.github.m5c.livepoll.pollutils.AlphabetSanitizer;
import com.github.m5c.livepoll.pollutils.Hyphenizer;
import java.io.FileInputStream;
import java.io.IOException;
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

/**
 * Thymeleaf endpoint for all web controllers, is to say all resources that forward to a thymeleaf
 * template and are strictly non-REST.
 *
 * @author Maximilian Schiedermeier
 */
@Controller
public class WebControllers {

  final PollManager pollManager;

  public WebControllers(@Autowired PollManager pollManager) {
    this.pollManager = pollManager;
  }

  /**
   * Root menu endpoint. Only available to localhsot.
   *
   * @param request
   * @return
   */
  @RequestMapping("/")
  public String forwardToMenu(HttpServletRequest request) {

    if (!isCallFromLocalhost(request)) {
      return "denied";
    } else {
      return "menu";
    }
  }

  /**
   * Quick poll endpoint, only accessible for localhost access / rejects foreign clients.
   *
   * @param request as the http request object used to determine the client's origin.
   * @return string corresponding to the target thymeleaf template.
   */
  @RequestMapping("/quick")
  public String quickPoll(HttpServletRequest request) {

    if (!isCallFromLocalhost(request)) {
      return "denied";
    } else {
      return "quick";
    }
  }

  /**
   * Thymeleaf endpoint for a specific poll. Only accessible from localhost.
   *
   * @param pollid  as the id of the poll to be rendered to a webpage.
   * @param model   as the thymeleaf model that stores variables in the server side page rendering.
   * @param request as the http request object needed to determine the client origin.
   * @return string corresponding to target thymeleaf template.
   */
  @RequestMapping("/polls/{pollid}")
  public String accessPoll(@PathVariable("pollid") String pollid, Model model,
                           HttpServletRequest request) {

    // TODO: figure out if you can define a @preauthorize role for that.
    // TODO: figure out if this causes issues with external IP combo (depends on significance of
    //  external IP, presumably, if only the clients are remote, there is no issue. Only is a
    //  porblem if the SERVER if remote and not on same machine as lecturers browser).
    // e.g. : https://stackoverflow.com/a/34411560
    if (!isCallFromLocalhost(request)) {
      return "denied";
    } else {
      if (pollManager.isExistentPoll(pollid)) {

        // Store information required to render in model, so that thymeleaf can insert it (server
        // sided)
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
   * Helper method to access QR code png files stored on disk. Local references are blocked by
   * browsers security policy, so we need an HTTP tunnel (reexposure of file on disk as web
   * resource).
   *
   * @param pollid  as the id of the qr code option to look up
   * @param option  as the selected vote option
   * @param request as the connection http opeject, required to identify connects form other
   *                machines and reject them.
   * @return ByteStream of QR png with required Meta media type.
   * @throws IOException in case the file is not found / cannot be read.
   */
  @GetMapping(value = "/polls/{pollid}/qr/{option}", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody byte[] getImageWithMediaType(@PathVariable("pollid") String pollid,
                                                    @PathVariable("option") String option,
                                                    HttpServletRequest request) throws IOException {

    // dont accept calls from elsewhere
    if (!isCallFromLocalhost(request)) {
      return null;
    }

    // Return the referenced QR code
    FileInputStream in =
        new FileInputStream(PollLauncher.pollTmpDir + '/' + pollid + "-" + option + ".png");
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
    // TODO: replace by @PreAuthorize annotation.
    return request.getRemoteAddr().equals("127.0.0.1");
  }
}
