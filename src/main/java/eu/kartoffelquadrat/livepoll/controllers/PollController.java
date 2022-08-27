package eu.kartoffelquadrat.livepoll.controllers;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import eu.kartoffelquadrat.livepoll.Poll;
import eu.kartoffelquadrat.livepoll.PollLauncher;
import eu.kartoffelquadrat.livepoll.PollManager;
import eu.kartoffelquadrat.livepoll.pollutils.AlphabetSanitizer;
import eu.kartoffelquadrat.livepoll.pollutils.Hyphenizer;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalIpResolver;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalResourceEncoder;
import eu.kartoffelquadrat.livepoll.qrgenerator.QrImageGenerator;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to register votes caused by scannign of QR codes.
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
   * REST endpoint to create a new poll, based on the options provided as body payload.   * Expects
   * a poll object in request body, serialized as json, e.g.: {"topic":"Are cats cooler than
   * dogs","optionVotes":{"No":0,"Yes":0}}
   *
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
   * REST endpoint to obtain details on an already existing poll. Can be loaded from frontend to
   * fill with poll data.
   *
   * @return poll object with all details.
   */
  @GetMapping(value = "/polls/{pollid}/topic", produces = "application/json; charset=utf-8")
  public String getPollTitle(@PathVariable("pollid") String pollid, HttpServletRequest request) {

    // reject if this request comes from a foreign machine.
    if (!request.getRemoteAddr().equals("127.0.0.1")) {
      return "GO AWAY!";
    }

    // Create new poll based on information in request payload.
    return pollManager.getPollByIdentifier(pollid).getTopic();
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

  /**
   * Helper method to get reference to this applications tempdir, where are local qr codes are
   * stored on filesystem. Is restricted to access from localhost.
   *
   * @return local filesystem reference to place where all qr-codes are stored as images.
   */
  @GetMapping("/qrdir")
  public String getPollQrTempDir(HttpServletRequest request) {

    // reject if this request comes from a foreign machine.
    if (!request.getRemoteAddr().equals("127.0.0.1")) {
      return "Go away!";
    }

    // return path to local tmp dir with all qr codes
    return PollLauncher.pollTmpDir;
  }
}
