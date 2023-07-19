package com.github.m5c.livepoll;

import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;

/**
 * Helper class to store all meta information for a poll, e.g. author, creation date, title etc.
 * Polls internally store their own meta, but meta can also be used for previews.
 */
public class PackMeta {

  // Marks the moment of creation (definition of the questions, not object instantiation)
  private final String creation;

  // A crisp title to rapidly identify the pack, e.g. "Lecture 01, Object Orientation"
  private final String title;

  // A short decriptive abstract of the learning goals for this pack.
  private final String packAbstract;

  // Author flag
  private final String author;

  /**
   * Constructor for pack meta. Consumes everything but the actuall poll questions.
   *
   * @param title        as the title to display for pack selection.
   * @param packAbstract as a one sentence description of the learning goals.
   * @param author       as the creator of the pack.
   */
  public PackMeta(String title, String packAbstract, String author) {
    creation = DateAndTopicIdGenerator.getFormattedDate();
    this.title = title;
    this.packAbstract = packAbstract;
    this.author = author;
  }

  public String getCreation() {
    return creation;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getPackAbstract() {
    return packAbstract;
  }
}
