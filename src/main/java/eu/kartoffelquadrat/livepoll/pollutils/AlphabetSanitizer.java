package eu.kartoffelquadrat.livepoll.pollutils;

/**
 * Helper class to sanitize topic strings from any characters that might be incompatible with
 * filesystem or URL-encoding.
 */
public class AlphabetSanitizer {

  /**
   * Takes an input string and strips it from all non alphanumeric or blank characters.
   *
   * @param nonAlphanumericString
   */
  public static String sanitize(String nonAlphanumericString) {
    // strip from any non alphanumeric character except whitespace
    // https://stackoverflow.com/a/6053606
    System.out.println("Input: " + nonAlphanumericString);
    String result = nonAlphanumericString.replaceAll("[^a-zA-Z\\d\\s:]", "");
    System.out.println("Output: "+result);
    return result;
  }
}
