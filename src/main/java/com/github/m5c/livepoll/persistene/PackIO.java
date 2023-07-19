package com.github.m5c.livepoll.persistene;

import com.github.m5c.livepoll.Pack;
import com.github.m5c.livepoll.pollutils.Hyphenizer;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Helper component to save and load pack files from disk.
 */
@Component
public class PackIO {

  @Value("${base.dir}")
  String baseDir;

  public void persistPackToDisk(Pack pack) throws IOException {

    // Serialize pack
    String serializedPack = new Gson().toJson(pack);

    // Create full path and filename (relative location in basedir, hyphenized name, json suffix)
    File samplePackFile = new File(
        System.getProperty("user.home") + "/" + baseDir + "/packs/" + pack.getCreation() + "-"
            + Hyphenizer.hyphenize(pack.getTitle()) + ".json");

    // Save to disk
    FileUtils.writeStringToFile(samplePackFile, serializedPack);
  }

  /**
   * Loads a pack object from disk. Searches the packs subdirectory of the basedir configured in
   * application.properties.
   *
   * @param fileName as the filename with file-extension of the pack (no full path, just the name).
   * @return deserialized pack object.
   * @throws IOException if the requested file does not exist.
   */
  public Pack loadPackFromDisk(String fileName) throws IOException {
    // Resolve full file path
    File targetPackFile =
        new File(System.getProperty("user.home") + "/" + baseDir + "/packs/" + fileName);

    // Load json string
    String packAsJson = FileUtils.readFileToString(targetPackFile);

    // Convert back to object and return
    return new Gson().fromJson(packAsJson, Pack.class);
  }
}
