package eu.kartoffelquadrat.livepoll;

import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PollLauncher {

    public static void main(String[] args) throws IOException, WriterException {
        SpringApplication.run(PollLauncher.class, args);
    }

}
