package eu.kartoffelquadrat.livepoll.pollutils;

/**
 * Helper class to sanitize topic strings from any characters that might be incompatible with
 * filesystem or URL-encoding.
 *
 * @author Maximilian Schiedermeier
 */
public class AlphabetSanitizer {

  /**
   * Takes an input string and strips it from all non alphanumeric or blank characters.
   *
   * @param nonAlphanumericString as the input string to filter.
   * @return same as input string, but without any alphanumeric characters.
   */
  public static String sanitize(String nonAlphanumericString) {
    // strip from any non alphanumeric character except whitespace
    // see: https://stackoverflow.com/a/6053606
    String result = nonAlphanumericString.replaceAll("[^a-zA-Z\\d\\s:]", "");
    return result;
  }
}
