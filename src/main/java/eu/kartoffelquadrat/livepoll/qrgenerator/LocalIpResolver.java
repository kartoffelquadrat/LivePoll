package eu.kartoffelquadrat.livepoll.qrgenerator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * Helper class to look up LAN IP address of this local machine, if and only if the LAN is connected
 * to the internet. If multiple network cards are used and they both connect to the internet the IP
 * of the first one indexed by the OS is returned.
 */
@Component
@Qualifier("localIpResolver")
public class LocalIpResolver implements IpResolver {

  /**
   * Helper method to look up that LAN IP address of the default network card used for internet
   * access. This is done be opening a test socket to the domain "google.com" and inspecting the
   * connection details.
   *
   * @return The LAN IP address.
   * @throws IOException in case the socket creation to "google.com" failed.
   */
  public String lookupIp() throws IOException {

    // Open a socket connection to google to look up network information.
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("google.com", 80));

    // Look up local address and strip leading slash.
    return socket.getLocalAddress().toString().replace("/", "");

  }
}
