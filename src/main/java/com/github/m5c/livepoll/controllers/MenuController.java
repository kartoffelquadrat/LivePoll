package com.github.m5c.livepoll.controllers;

import com.github.m5c.livepoll.Pack;
import com.github.m5c.livepoll.PackMeta;
import com.github.m5c.livepoll.Poll;
import com.github.m5c.livepoll.persistence.PackPersistence;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for all mene backend interactions.
 *
 * @author Maximilian Schiedermeier
 */
@RestController
public class MenuController {

  private Logger logger = LoggerFactory.getLogger(MenuController.class);
  private PackPersistence packPersistence;

  private PollController pollController;

  /**
   * Public constructor to inject dependencies. We use constructor injection to allow for simple
   * testing outside Springs bean container.
   *
   * @param pollController  to delegate operations on individual polls associated to packs.
   * @param packPersistence as service component that enables fast pack IO operations.
   */
  public MenuController(@Autowired PackPersistence packPersistence,
                        @Autowired PollController pollController) {
    this.packPersistence = packPersistence;
    this.pollController = pollController;
  }

  /**
   * REST endpoint to retrieve list with metainformation for all packs. UI can display meta
   * information and to allow user to select the desired pack.
   *
   * @return Map of all loaded PackMetas to their filename (without leading path information).
   * @throws IOException in case pack information can not be loaded from disk.
   */
  @GetMapping("/menu/packs")
  public Map<String, PackMeta> getPreparedPacks() throws IOException {

    return packPersistence.loadAllPackMetas();
  }

  /**
   * REST endpoint to instantiate new poll endpoints for all elements of a provided pack.
   *
   * @param packFileName of the pack in the defailt pack location folder. This must not a path.
   * @return List of poll IDs for all instantiated polls from pack.
   * @throws IOException     in case a pack cannot be found on disk.
   * @throws WriterException in case a poll cannot be registered with the PollManager.
   */
  @PutMapping("/menu/packs/{packFileName}")
  public List<String> instantiatePackFromFile(@PathVariable("packFileName") String packFileName)
      throws IOException, WriterException {

    logger.info(
        "Loading pack from disk and instantiating all associated poll endpoints: " + packFileName);
    Pack loadedPack = packPersistence.loadPackFromDisk(packFileName);

    // Create new poll instance for every question stored in pack
    LinkedList packInstancePollIds = new LinkedList();
    for (Poll poll : loadedPack.getQuestions()) {

      // Register poll object with backend, that is to say create a unique ID that can be used for
      // interaction via REST and create QR codes for voting.
      String pollId = pollController.registerPoll(poll);
      packInstancePollIds.add(pollId);
    }

    return packInstancePollIds;
  }
}
