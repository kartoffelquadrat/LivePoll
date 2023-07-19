package com.github.m5c.livepoll.pollutils;

/**
 * PollIdGenerator interface. Decided for an interface since there might be other name generators in
 * the future, based on various criteria.
 *
 * @author Maximilian Schiedermeier
 */
public interface IdGenerator {

  /**
   * Abstract method to create poll id based on implementing classes algorithm and provided theme.
   *
   * @param theme as a string describing the polls topic in human readable language.
   * @return unique id for this poll, based on the concrete implementation fo this abstract class.
   */
  String generateId(String theme);
}
