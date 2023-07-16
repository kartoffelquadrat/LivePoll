package com.github.m5c.livepoll.pollutils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * Extension of the PollIdGenerator abstract class. Uses a date prefix for the PollID.
 *
 * @author Maximilian Schiedermeier
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

    // TODO: Sanitize topic from all non ascii characters.

    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    date += "-" + Hyphenizer.hyphenize(AlphabetSanitizer.sanitize(theme));
    return date;
  }
}
