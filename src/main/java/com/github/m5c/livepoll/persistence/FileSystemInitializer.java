package com.github.m5c.livepoll.persistence;

import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Helper class to ensure the required file system stucture for user config files and persisted data
 * is present at program startup.
 */
@Component
public class FileSystemInitializer {

  // Inject base dir value from property file
  private String baseDir;

  private final DateAndTopicIdGenerator idGenerator;

  private final PackPersistence packPersistence;

  /**
   * Component constructor.
   *
   * @param idGenerator     as a component that turns polls to their hyphenized unique ids.
   * @param packPersistence as service object for save / load of packs to the filesystem.
   * @param baseDir         as the applications base directory for config and IO operations.
   */
  public FileSystemInitializer(@Autowired DateAndTopicIdGenerator idGenerator,
                               @Autowired PackPersistence packPersistence,
                               @Value("${base.dir}") String baseDir) {
    this.idGenerator = idGenerator;
    this.packPersistence = packPersistence;
    this.baseDir = baseDir;
  }

  /**
   * Actual logic resides in postconstruct, so we can access values injected from porperties file.
   * Postconstruct is autaomtically triggered after class creation through spring, for this class is
   * a component.
   *
   * @throws IOException in case the file system access fails for either verification or repair.
   */
  @PostConstruct
  public void ensureBaseDirIsReady() throws IOException {

    // Ensure the required file / directory structure is present.
    String prefix = (baseDir.startsWith("/") ? "" : System.getProperty("user.home"));
    File baseDirFile = new File(prefix + "/" + baseDir);
    File configfileFile = new File(baseDirFile.getPath() + "/config.properties");
    File packsDirFile = new File(baseDirFile.getPath() + "/packs");

    ensureDirExists(baseDirFile);
    ensureFileExists(configfileFile);
    ensureDirExists(packsDirFile);
  }

  /**
   * Helper method that verifies the file system and adjusts structure if the requested folder is
   * not in place.
   *
   * @param dir as the folder object to check for on the file system as directory.
   * @throws IOException in case filesystem access fails.
   */
  private void ensureDirExists(File dir) throws IOException {
    // if dir exits but is a file, delete it
    if (dir.exists() && !FileUtils.isDirectory(dir)) {
      FileUtils.delete(dir);
    }

    // if dir does not exist, create new directory
    if (!FileUtils.isDirectory(dir)) {
      FileUtils.forceMkdir(dir);
    }
  }

  /**
   * Helper method that verifies the file system and adjusts structure if the requested non-folder
   * file is not in place.
   *
   * @param file as the file object to check for on the file system as directory.
   * @throws IOException in case filesystem access fails.
   */

  private void ensureFileExists(File file) throws IOException {
    // if file exits but is a dir, delete it
    if (file.exists() && FileUtils.isDirectory(file)) {
      FileUtils.delete(file);
    }

    // if file does not exist, create new directory
    if (!FileUtils.isDirectory(file)) {
      FileUtils.touch(file);
    }
  }
}