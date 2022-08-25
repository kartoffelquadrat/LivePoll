package eu.kartoffelquadrat.livepoll.qrgenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Helper class to build unique http resource strings.
 */
@Component
public class LocalResourceEncoder {

    @Autowired
    LocalIpResolver localIpResolver;

    @Value("${server.port}")
    String port;

    public String buildResourceString(long pollId, String resource) throws IOException {

        // replace any whitespaces in resource strings
        resource.replaceAll("\\s+", "-");

        // Actually compose the resource string
        StringBuilder resourceStringBuilder = new StringBuilder("http://");
        resourceStringBuilder.append(localIpResolver.lookupOwnLocalAreaNetworkIp());
        resourceStringBuilder.append(":").append(port);
        resourceStringBuilder.append("/").append(pollId);
        resourceStringBuilder.append("/").append(resource);
        return resourceStringBuilder.toString();
    }
}
