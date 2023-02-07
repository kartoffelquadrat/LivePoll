package eu.kartoffelquadrat.livepoll.qrgenerator;

import java.io.IOException;

/**
 * Common interface for all IP resolvers.
 */
public interface IpResolver {

  /**
   * Method to look up actual IP. Implementation differs based on whether the LAN ip or access point
   * is requested.
   *
   * @return string provideding the ip address.
   * @throws IOException in case the ip resolving failed (e.g. no network interface found).
   */
  String lookupIp() throws IOException;

}
