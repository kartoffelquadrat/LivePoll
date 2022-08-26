package eu.kartoffelquadrat.livepoll.pollutils;

/**
 * Utils class to help convert strings into resource / file system friendly format.
 */
public class Hyphenizer {

  /**
   * Helper method to strip any topic to a kebap notation so it is save for usage in filenames.
   *
   * @param humanReadableString any string in natural language.
   * @return the kebap notated version of the original human readable string.
   */
  public static String hyphenize(String humanReadableString) {

    // Remove any trailing or leading whitespaces
    humanReadableString = humanReadableString.trim();

    // Replace all whitespaces by hyphens
    humanReadableString = humanReadableString.replaceAll("\\s+", "-");

    // make all lowercase
    humanReadableString = humanReadableString.toLowerCase();

    // Strip all non [a-zA-Z] or hyphen characters.
    return humanReadableString.replaceAll("\\[a-zA-Z|-]", "");
  }
}
