package eu.kartoffelquadrat.livepoll;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents all static and dynmically collected data of a poll.
 */
public class Poll {

  private final String topic;
  private final Map<String, Integer> optionVotes;


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
   * @returs all available options in order.
   */
  public String[] getOptions() {
    return optionVotes.keySet().toArray(new String[0]);
  }


  /**
   * Returns vote amount for a given option.
   *
   * @param option the string for the selected poll response option.
   */
  public int voteForOption(String option) {
    return optionVotes.get(option);
  }
}
