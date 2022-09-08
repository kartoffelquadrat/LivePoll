package eu.kartoffelquadrat.livepoll.controllers;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import eu.kartoffelquadrat.livepoll.Poll;
import eu.kartoffelquadrat.livepoll.PollManager;
import eu.kartoffelquadrat.livepoll.pollutils.AlphabetSanitizer;
import eu.kartoffelquadrat.livepoll.pollutils.Hyphenizer;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalIpResolver;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalResourceEncoder;
import eu.kartoffelquadrat.livepoll.qrgenerator.QrImageGenerator;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to register votes caused by scanning of QR codes.
 *
 * @author Maximilian Schiedermeier
 */
@RestController
public class PollController {

  QrImageGenerator qrImageGenerator;

  LocalResourceEncoder localResourceEncoder;

  LocalIpResolver localIpResolver;

  PollManager pollManager;

  /**
   * Bean constructor.
   *
   * @param qrImageGenerator     as the generator to be used for the creation of QR BitMatrix
   *                             objects from resource strings.
   * @param localResourceEncoder as the encoder to be used to generate URI strings for vote option
   *                             resources.
   * @param localIpResolver      asa helper tool to look up the own LAN IP address of this
   *                             webservice.
   * @param pollManager          as the manager object that indexes all active polls.
   */
  @Autowired
  public PollController(QrImageGenerator qrImageGenerator,
                        LocalResourceEncoder localResourceEncoder,
                        LocalIpResolver localIpResolver,
                        PollManager pollManager) {
    this.qrImageGenerator = qrImageGenerator;
    this.localResourceEncoder = localResourceEncoder;
    this.localIpResolver = localIpResolver;
    this.pollManager = pollManager;
  }


  /**
   * An actual endpoint, referenced by generated QR code. Note using Get operation here is a clear
   * violation to the REST style, but since we want to support vote by QR scanning it has to be GET
   * (default HTTP method for browser resource access).
   *
   * @param pollId as the unique identifier of the poll for which we want to register a vote
   * @param option as the clients vote option submitted for this call
   * @return string confirming the successful registration of the received ballot
   */
  @GetMapping("/polls/{pollid}/options/{option}")
  public String registerVote(@PathVariable("pollid") String pollId,
                             @PathVariable("option") String option) {

    pollManager.getPollByIdentifier(pollId).voteForOption(option);
    return "I registered your vote for \"" + option
        + "\". Thank you for your participation. You can leave this page now. Please don't refresh"
        + " the page.";
  }

  /**
   * REST endpoint to look up amount of votes per option. Endpoint is better than static rendering
   * on server side. Only accessible from localhost.
   *
   * @param pollId  as the unique identifier of the poll for which we want to register a vote
   * @param option  as the clients vote option submitted for this call
   * @param request as the http connection meta bundle, providing information on the sender
   * @return the amount of votes registered so far for the specified vote and option
   */
  @GetMapping("/polls/{pollid}/outcome/{option}")
  public int getVoteAmount(@PathVariable("pollid") String pollId,
                           @PathVariable("option") String option, HttpServletRequest request) {

    // only treat if request form local machine and poll id valid
    if (request.getRemoteAddr().equals("127.0.0.1")) {
      if (pollManager.isExistentPoll(pollId)) {
        return pollManager.getPollByIdentifier(pollId).getVotes(option);
      }
    }

    // return bogus number instead
    return -42;
  }


  /**
   * REST endpoint to create a new poll, based on the options provided as body payload.   * Expects
   * a poll object in request body, serialized as json, e.g.: {"topic":"Are cats cooler than
   * dogs","optionVotes":{"No":0,"Yes":0}}
   *
   * @param poll    as request body with all input details required to create the new poll
   * @param request as http servlet meta info object, providing information about client origin
   * @return id of the newly created poll.
   * @throws IOException     in case the implicit lookup of the pwn webapps LAN ip failed.
   * @throws WriterException in case the writing of a QR png file to the file system failed.
   */

  @PostMapping(value = "/polls", consumes = "application/json; charset=utf-8")
  public String createPoll(@RequestBody Poll poll, HttpServletRequest request)
      throws IOException, WriterException {

    // reject if this request comes from a foreign machine.
    if (!request.getRemoteAddr().equals("127.0.0.1")) {
      return "Go away!";
    }

    // Create new poll based on information in request payload.
    String pollId = pollManager.addPoll(poll);
    createPollQrCodes(pollId, poll);
    return pollId;
  }

  /**
   * Private helper method to generate the qrcodes for a poll option and store the qr files on
   * disk.
   *
   * @param poll object for which qrcodes are required. No path information is required since all
   *             qrcode names consist of poll identifier+option in kebap notation.
   * @throws IOException     lookup of qr target IP fails
   * @throws WriterException if writing of qrcode to disk fails
   */
  private void createPollQrCodes(String pollId, Poll poll)
      throws IOException, WriterException {

    // Create QR code for every option mentioned in poll
    for (String option : poll.getOptions()) {

      // convert option to kebab notation
      String optionResource = Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(option));

      // Generate QR code and store on disk
      String resourceString = localResourceEncoder.buildResourceString(pollId, optionResource);
      BitMatrix qrMatrix = qrImageGenerator.encodeQr(resourceString);
      String qrFileName = pollId + "-" + optionResource;
      qrImageGenerator.exportQrToDisk(qrFileName, qrMatrix);
    }
  }
}
