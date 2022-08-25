package eu.kartoffelquadrat.livepoll.pollutils;

public abstract class PollIdGenerator {

  /**
   * Abstract method to create poll id based on implementing classes algorithm and provided theme.
   *
   * @param theme as a string describing the polls topic in human readable language.
   * @return the id unique for this poll, based on the concrete implementation fo this abstract
   * class.
   */
  public abstract String generatePollId(String theme);

  /**
   * Helper method to strip any topic to a kebap notation so it is save for usage in filenames.
   *
   * @param humanReadableString any string in natural language.
   * @return the kebap notated version of the original human readable string.
   */
  protected String hyphenize(String humanReadableString) {

    // Remove any trailing or leading whitespaces
    humanReadableString = humanReadableString.trim();

    // Replace all whitespaces by hyphens
    humanReadableString = humanReadableString.replaceAll("\\s+", "-");

    // Strip all non [a-zA-Z] or hyphen characters.
    return humanReadableString.replaceAll("\\[a-zA-Z|-]", "");
  }
}
