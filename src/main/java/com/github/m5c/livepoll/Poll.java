package com.github.m5c.livepoll;

import com.github.m5c.livepoll.pollutils.AlphabetSanitizer;
import com.github.m5c.livepoll.pollutils.Hyphenizer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents all static and dynmically collected data of a poll. Note that one poll can be cast
 * multiple times and therefore the URL associated to a poll is not stored inside the poll object.
 *
 * @author Maximilian Schiedermeier
 */
public class Poll {

  // The question to ask
  private final String topic;

  // The optionVotes map serves at once as list of descriptive strings, and count for submitted
  // votes. The count is initialized to zero ar object creation
  private final Map<String, Integer> optionVotes;

  // Marks the index of the correct option
  private final int solutionIndex;

  private final String explanation;


  /**
   * Constructor. Sets up topic and options/vote-amount map. This constructor sets solution index
   * and explanation to default/empty values.
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
    // Default to negative value, to indivate there is no marked correct solution.
    solutionIndex = -1;
    explanation = "";
  }

  /**
   * Overloaded contructor that also allows setting of solution and explanation.
   *
   * @param topic         as the final topic for this poll.
   * @param options       as the string options for all possible answers.
   * @param solutionIndex as the number of the correct answer.
   * @param explanation   as additional text to jsutify the right answer.
   */
  public Poll(String topic, String[] options, int solutionIndex, String explanation) {

    // Store topic and initialize registered votes for all options to 0.
    this.topic = topic;
    this.optionVotes = new LinkedHashMap<>();
    for (String option : options) {
      this.optionVotes.put(option, 0);
    }
    this.solutionIndex = solutionIndex;
    this.explanation = explanation;
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
   * Helper method to find the raw option (option in human-readable form) that matches a porivided
   * sanitized from, as e g received from a QR code.
   *
   * @param sanitized the sanitized asn hyphenized option to look up
   * @return the human-readable string option the provided sanitized option
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

  /**
   * Getter to look up the index of the right answer entry in the optionVotes map.
   *
   * @return int as index.
   */
  public int getSolutionIndex() {
    return solutionIndex;
  }

  /**
   * Getter to look up justification of the correct answer.
   *
   * @return String as explanation.
   */
  public String getExplanation() {
    return explanation;
  }
}
