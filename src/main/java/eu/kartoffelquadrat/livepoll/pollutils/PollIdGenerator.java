package eu.kartoffelquadrat.livepoll.pollutils;

public interface PollIdGenerator {

  /**
   * Abstract method to create poll id based on implementing classes algorithm and provided theme.
   *
   * @param theme as a string describing the polls topic in human readable language.
   * @return the id unique for this poll, based on the concrete implementation fo this abstract
   * class.
   */
  String generatePollId(String theme);
}
