package eu.kartoffelquadrat.livepoll.controllers;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalIpResolver;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalResourceEncoder;
import eu.kartoffelquadrat.livepoll.qrgenerator.QrImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PollController {

    @Autowired
    QrImageGenerator qrImageGenerator;

    @Autowired
    LocalResourceEncoder localResourceEncoder;

    @Autowired
    LocalIpResolver localIpResolver;

    /**
     * This one should actually be triggered by thymeleaf access on poll / generation of new poll.
     *
     * @return String pointing to QR code file.
     * @throws IOException
     * @throws WriterException
     */
    @GetMapping("toto")
    public String toto() throws IOException, WriterException {

        String resourceString = localResourceEncoder.buildResourceString(42, "foo");
        BitMatrix qrMatrix = qrImageGenerator.encodeQr(resourceString);
        return qrImageGenerator.exportQrToDisk("test.png", qrMatrix);
    }

    /**
     * An actual enpoint, referenced by generated QR code.
     */
    @GetMapping("{pollid}/{vote}")
    public String registerVote(@PathVariable("vote") String vote) {
        return "I registered your vote \"" + vote + "\". Thank you for your participation.";
    }
}
