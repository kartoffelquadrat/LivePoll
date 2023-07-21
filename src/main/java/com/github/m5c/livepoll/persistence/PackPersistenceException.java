package com.github.m5c.livepoll.persistence;

/**
 * Custom exception to mark errors with handling of pack files.
 *
 * @author Maximilian Schiedermeier
 */
public class PackPersistenceException extends Throwable {

  /**
   * Default constructor.
   *
   * @param s as error cause.
   */
  public PackPersistenceException(String s) {
    super(s);
  }
}
