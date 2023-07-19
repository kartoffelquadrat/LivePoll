package com.github.m5c.livepoll.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.m5c.livepoll.Pack;
import java.util.Collection;

/**
 * REST Controller for all mene backend interactions.
 *
 * @author Maximilian Schiedermeier
 */
@RestController
public class MenuController {

  @GetMapping()
  public Collection<Pack> getPreparedPacks() {

    // TODO: look up packs on file system.

    // TODO: load all packs and store in collection (each pack is a file)

    // TODO: create super collection and return map of packs (title - object)
    return null;
  }

  // TODO: endpoint to launch ONE loaded pack, i.e. use poll-manager to index each poll contained in pack.
}
