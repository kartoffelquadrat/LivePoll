package com.github.m5c.livepoll.qrgenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * IP resolver implementation rthat looks up public IP address (access point), used by this
 * machine.
 */
@Component
@Qualifier("externalIpResolver")
public class ExternalIpResolver implements IpResolver {

  /**
   * Ip lookup implementation that contacts an AWS server to determine the external IP.
   *
   * @return String with IP address.
   * @throws IOException if the AWS server can not be reached.
   */
  @Override
  public String lookupIp() throws IOException {

    // Looks up external IP by contacting AWS service. See: https://stackoverflow.com/a/2939223
    URL whatismyip = new URL("http://checkip.amazonaws.com");
    BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

    String ip = in.readLine(); //you get the IP as a String
    return ip;
  }
}
