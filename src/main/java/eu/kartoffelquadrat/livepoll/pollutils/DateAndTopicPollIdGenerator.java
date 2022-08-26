package eu.kartoffelquadrat.livepoll.pollutils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Extention of the PollIdGenerator abstract class. Uses a date prefix for the PollID.
 */
@Component
public class DateAndTopicPollIdGenerator implements PollIdGenerator {

  /**
   * Concrete implementation of the abstract superclass. Uses the date-string as prefix and topic
   * description as suffix. Converts the concatenation to kebap notation.
   *
   * @param theme as a string describing the polls topic in human readable language.
   * @return kebap notated concatenation of YYYY-MM-DD and the topic.
   */
  @Override
  public String generatePollId(String theme) {

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    date += "-" + Hyphenizer.hyphenize(theme);
    return date;
  }
}
