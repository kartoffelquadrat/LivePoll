package eu.kartoffelquadrat.livepoll.qrgenerator;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

/**
 * Helper class to look up LAN IP address of this local machine, if and only if the LAN is connected to the internet.
 * If multiple network cards are used and they both connect to the internet the IP of the first one indexed by the OS
 * is returned.
 */
@Component
public class LocalIpResolver {

    public String lookupOwnLocalAreaNetworkIp() throws IOException {

        // Open a socket connection to google to look up network information.
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));

        // Look up local address and strip leading slash.
        return socket.getLocalAddress().toString().replace("/", "");

    }
}
