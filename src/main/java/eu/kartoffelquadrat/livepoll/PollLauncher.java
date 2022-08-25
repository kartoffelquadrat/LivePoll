package eu.kartoffelquadrat.livepoll;

import eu.kartoffelquadrat.livepoll.qrgenerator.LocalIpResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.UnknownHostException;

@SpringBootApplication
public class PollLauncher {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PollLauncher.class, args);
		System.out.println(LocalIpResolver.lookupOwnLocalAreaNetworkIp());
	}

}
