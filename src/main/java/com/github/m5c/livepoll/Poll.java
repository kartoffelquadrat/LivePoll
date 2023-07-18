package com.github.m5c.livepoll;

import com.github.m5c.livepoll.pollutils.AlphabetSanitizer;
import com.github.m5c.livepoll.pollutils.Hyphenizer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents all static and dynmically collected data of a poll.
 *
 * @author Maximilian Schiedermeier
 */
public class Poll {

  private final String topic;
  private final Map<String, Integer> optionVotes;

  // TODO: add field for correct option index.
  // TODO: add field for explanation of correct option.


  /**
   * Constructor. Sets up topic and options/vote-amount map.
   *
   * @param topic   as the final topic for this poll.
   * @param options as the string options for all possible answers.
   */
  public Poll(String topic, String[] options) {

    // Store topic and initialize registered votes for all options to 0.
    this.topic = topic;
    this.optionVotes = new LinkedHashMap<>();
    for (String option : options) {
      this.optionVotes.put(option, 0);
    }
  }

  /**
   * Getter for this poll's topic.
   *
   * @return the string representing this poll's topic.
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Returns string array with all possible options for this poll.
   *
   * @return all available options in order.
   */
  public String[] getOptions() {
    return optionVotes.keySet().toArray(new String[0]);
  }


  /**
   * Adds a vote for a given option.
   *
   * @param option the string for the selected poll response option.
   */
  public void voteForOption(String option) {

    String rawOptionMatch = findRawMatch(option);
    optionVotes.put(rawOptionMatch, optionVotes.get(rawOptionMatch) + 1);
  }

  /**
   * Helper method to find the raw option (option in human readable form) that matches a porivided
   * sanitized from, as e g received from a QR code.
   *
   * @param sanitized the sanitized asn hyphenized option to look up
   * @return the human readable string option the provided sanitized option
   */
  private String findRawMatch(String sanitized) {
    // options in poll object are not encoded in sanitize / hyphenized form. Must iterate to lookup.
    for (String rawOption : optionVotes.keySet()) {

      if (Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(rawOption)).equals(sanitized)) {
        return rawOption;
      }
    }
    return null;
  }

  /**
   * Getter to look up the amount of votes for a given option.
   *
   * @param option as the sanitized / hyphenized option to look up.
   * @return the amount of votes received for this option so far.
   */
  public int getVotes(String option) {

    String rawMatch = findRawMatch(option);
    return optionVotes.get(rawMatch);
  }
}
