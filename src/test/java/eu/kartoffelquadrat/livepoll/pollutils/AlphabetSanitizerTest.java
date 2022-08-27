package eu.kartoffelquadrat.livepoll.pollutils;

import org.junit.Assert;
import org.junit.Test;

public class AlphabetSanitizerTest {

  @Test
  public void sanitizeTest() {
    String input = "#*+!$&^ kasjdhf KJD-SHF";
    String sanitizedInput = " kasjdhf KJDSHF";
    String output = AlphabetSanitizer.sanitize(input);
    System.out.println(output);
    Assert.assertTrue(
        "Expected sanitized output: \"" + sanitizedInput + "\" but received: \"" + output + "\"",
        output.equals(sanitizedInput));
  }
}
