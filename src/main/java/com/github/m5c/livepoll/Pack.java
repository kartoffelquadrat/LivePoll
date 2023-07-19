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

  // Marks the moment of creation (definition of the questions, not object instantiation)
  private final String creation;

  // A crisp title to rapidly identify the pack, e.g. "Lecture 01, Object Orientation"
  private final String title;

  // A short decriptive abstract of the learning goals for this pack.
  private final String pollAbstract;

  // Series of questions to ask when replaying this pack.
  private final Collection<Poll> questions;

  // Author flag
  private final String author;

  public Pack(String title, String pollAbstract, String author, Collection<Poll> questions) {
    this.creation = DateAndTopicIdGenerator.getFormattedDate();
    this.title = title;
    this.pollAbstract = pollAbstract;
    this.author = author;
    this.questions = questions;
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

  public String getPollAbstract() {
    return pollAbstract;
  }

  public Collection<Poll> getQuestions() {
    return Collections.unmodifiableCollection(questions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Pack Info:\n");
    sb.append("Title: "+getTitle()+"\n");
    sb.append("Created: "+getCreation()+"\n");
    sb.append("Author: "+getAuthor()+"\n");
    sb.append("Abstract: "+getPollAbstract()+"\n");
    sb.append("Questions: \n");
    for(Poll poll: getQuestions())
    {
      sb.append("\t> "+poll.getTopic()+"\n");
      for(String option: poll.getOptions())
      {
        sb.append("\t\t- "+option+"\n");
      }
    }
    return sb.toString();
  }
}
