package com.github.m5c.livepoll;

import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import com.github.m5c.livepoll.pollutils.IdGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Spring component responsible for storage of all polls, no matter if ongoing or terminated.
 *
 * @author Maximilian Schiedermeier
 */
@Component
public class PollManager {

  private final Map<String, Poll> indexedPolls;

  IdGenerator idGenerator;

  /**
   * PollManager constructor.
   *
   * @param idGenerator id generator that provides index strings for polls that are offered to the
   *                    manager.
   */
  public PollManager(@Autowired DateAndTopicIdGenerator idGenerator) {
    this.indexedPolls = new LinkedHashMap<>();
    this.idGenerator = idGenerator;
  }

  /**
   * Getter to obtain a specific poll object by it's unique id.
   *
   * @param pollId as the poll id to search for during lookup.
   * @return the poll object associated to the id if found. Null instead.
   */
  public Poll getPollByIdentifier(String pollId) {

    if (indexedPolls.containsKey(pollId)) {
      return indexedPolls.get(pollId);
    }
    return null;
  }

  /**
   * Adds a new poll to the manager. Builds a unique id using the autowired id generator.
   *
   * @param poll as the new poll to add.
   * @return the unique poll id generated for the newly created poll.
   */
  public String addPoll(Poll poll) {

    String pollId = idGenerator.generateId(poll.getTopic());
    indexedPolls.put(pollId, poll);
    return pollId;
  }

  /**
   * Looks up if the given poll is known to the manager.
   *
   * @param pollid as the poll keyname to look up.
   * @return true if the poll exists, false if not.
   */
  public boolean isExistentPoll(String pollid) {
    return indexedPolls.containsKey(pollid);
  }
}
