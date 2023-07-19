package com.github.m5c.livepoll.controllers;

import com.github.m5c.livepoll.PackMeta;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for all mene backend interactions.
 *
 * @author Maximilian Schiedermeier
 */
@RestController
public class MenuController {

  /**
   * REST endpoint to retrieve list with metainformation for all packs. UI can display meta
   * information and to allow user to select the desired pack.
   *
   * @return List of PackMeta details.
   */
  @GetMapping("/menu/packs") // TODO check if this is the right URL
  public Collection<PackMeta> getPreparedPacks() {

    // TODO: look up packs on file system.

    // TODO: load all packs and store in collection (each pack is a file)

    // TODO: create super collection and return map of packs (title - object)
    return null;
  }

  // TODO: endpoint to launch ONE loaded pack, i.e. use poll-manager to index each poll
  //  contained in pack.
}
