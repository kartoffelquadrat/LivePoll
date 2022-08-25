package eu.kartoffelquadrat.livepoll;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import eu.kartoffelquadrat.livepoll.qrgenerator.LocalIpResolver;
import eu.kartoffelquadrat.livepoll.qrgenerator.QrImageGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PollLauncher {

    public static void main(String[] args) throws IOException, WriterException {
        SpringApplication.run(PollLauncher.class, args);
        String localIp = LocalIpResolver.lookupOwnLocalAreaNetworkIp();
        BitMatrix qrMatrix = QrImageGenerator.encodeQr(localIp);
        QrImageGenerator.exportQrToDisk("test.svg", qrMatrix);
    }

}
