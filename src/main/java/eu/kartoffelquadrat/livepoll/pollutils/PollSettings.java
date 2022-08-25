package eu.kartoffelquadrat.livepoll.pollutils;

/**
 * Transfer object to encapsulate all poll settings required or setup.
 */
public class PollSettings {

  private final String topic;
  private final String[] options;

  /**
   * Constructor for PollSettings transfer object.
   *
   * @param topic   as a human readable description of the topic.
   * @param options as the full list of poll vote options, encoded as human readable strings.
   */
  public PollSettings(String topic, String[] options) {
    this.topic = topic;
    this.options = options;
  }

  /**
   * Getter for the topic attribute of a poll settings transfer object.
   *
   * @return human readable description of the poll topic.
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Getter for all options of a poll settings transfer object.
   *
   * @return string array with all possible vote options.
   */
  public String[] getOptions() {
    return options;
  }
}
