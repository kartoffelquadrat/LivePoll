package com.github.m5c.livepoll;

import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import java.util.Collection;
import java.util.Collections;

/**
 * Pack defines a series of poll objects. Poll objects may be reused across packs. This class
 * deginates the pack object as persisted to disk. For additional fiels that allow loading of a pack
 * at runtima and association with URLs, see LoadedPack extension.
 *
 * @author Maximilian Schiedermeier
 */
public class Pack {

  private final PackMeta meta;
  private final Collection<Poll> questions;


  public Pack(PackMeta meta, Collection<Poll> questions) {
    this.meta = meta;
    this.questions = questions;
  }

  public Collection<Poll> getQuestions() {
    return Collections.unmodifiableCollection(questions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Pack Info:\n");
    sb.append("Title: " + meta.getTitle() + "\n");
    sb.append("Created: " + meta.getCreation() + "\n");
    sb.append("Author: " + meta.getAuthor() + "\n");
    sb.append("Abstract: " + meta.getPackAbstract() + "\n");
    sb.append("Questions: \n");
    for (Poll poll : getQuestions()) {
      sb.append("\t> " + poll.getTopic() + "\n");
      for (String option : poll.getOptions()) {
        sb.append("\t\t- " + option + "\n");
      }
    }
    return sb.toString();
  }

  public PackMeta getMeta() {
    return meta;
  }
}
