package eu.kartoffelquadrat.livepoll.qrgenerator;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Helper class to build unique http resource strings.
 */
@Component
public class LocalResourceEncoder {

  final IpResolver ipResolver;

  @Value("${server.port}")
  String port;

  /**
   * Constructor for resource encoder. Consumes both implementation os op resolvers and decides at
   * runtime which one to uise, based on porperties file.
   *
   * @param localIpResolver    as a resolver to retreive the LAN IP.
   * @param externalIpResolver as a resolver to retreive the acces point IP.
   */
  public LocalResourceEncoder(@Value("${ip.useexternal}") boolean useExternalIp,
                              @Autowired LocalIpResolver localIpResolver,
                              @Autowired ExternalIpResolver externalIpResolver) {

    if (useExternalIp) {
      ipResolver = externalIpResolver;
    } else {
      ipResolver = localIpResolver;
    }
  }

  /**
   * Builds an HTTP URI string using localhost's IP and this webapps port number.
   *
   * @param pollId   as previously generated string that encodes the poll id
   * @param resource as the name of the resource (should be a specific option represented by a
   *                 vote)
   * @return the created URL string.
   * @throws IOException in case the lookup of the localhost IP address failed.
   */
  public String buildResourceString(String pollId, String resource) throws IOException {

    // replace any whitespaces in resource strings
    resource = resource.replaceAll("\\s+", "-");

    // Actually compose the resource string
    return "http://" + ipResolver.lookupIp() + ":" + port + "/polls/" + pollId + "/options/"
        + resource;
  }
}
