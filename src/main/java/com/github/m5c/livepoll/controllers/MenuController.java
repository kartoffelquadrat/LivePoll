package com.github.m5c.livepoll.controllers;

import com.github.m5c.livepoll.PackMeta;
import com.github.m5c.livepoll.persistence.PackPersistence;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for all mene backend interactions.
 *
 * @author Maximilian Schiedermeier
 */
@RestController
public class MenuController {

  private PackPersistence packPersistence;

  /**
   * Public constructor to inject dependencies. We use constructor injection to allow for simple
   * testing outside Springs bean container.
   *
   * @param packPersistence as service component that enables fast pack IO operations.
   */
  public MenuController(@Autowired PackPersistence packPersistence) {
    this.packPersistence = packPersistence;
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
}
