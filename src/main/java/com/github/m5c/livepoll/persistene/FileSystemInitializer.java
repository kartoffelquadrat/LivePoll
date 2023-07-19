package com.github.m5c.livepoll.persistene;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Helper class to ensure the required file system stucture for user config files and persisted data
 * is present at program startup.
 */
@Component
public class FileSystemInitializer {

  // Inject base dir value from property file
  @Value("${base.dir}")
  private String baseDir;

  /**
   * Actual logic resides in postconstruct, so we can access values injected from porperties file.
   * Postconstruct is autaomtically triggered after class creation through spring, for this class is
   * a component.
   */
  @PostConstruct
  public void ensureBaseDirIsReady() throws IOException {

    // Ensure the required file / directory structure is present.
    File baseDirFile = new File(System.getProperty("user.home") + "/" + baseDir);
    File configfileFile = new File(baseDirFile.getPath() + "/config.properties");
    File packsDirFile = new File(baseDirFile.getPath() + "/packs");

    ensureDirExists(baseDirFile);
    ensureFileExists(configfileFile);
    ensureDirExists(packsDirFile);
  }

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