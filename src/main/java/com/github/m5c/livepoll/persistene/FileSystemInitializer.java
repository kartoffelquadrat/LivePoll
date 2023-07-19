package com.github.m5c.livepoll.persistene;

import com.github.m5c.livepoll.Pack;
import com.github.m5c.livepoll.Poll;
import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import com.github.m5c.livepoll.pollutils.Hyphenizer;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
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
  @Value("${base.dir}")
  private String baseDir;

  private final DateAndTopicIdGenerator idGenerator;

  private final PackIO packIO;

  public FileSystemInitializer(@Autowired DateAndTopicIdGenerator idGenerator,
                               @Autowired PackIO packIO) {
    this.idGenerator = idGenerator;
    this.packIO = packIO;
  }

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

    // Create and persist sample pack
    Poll poll1 =
        new Poll("Are cats cooler than dogs?", new String[] {"Yes", "YES!", "Absolutely"}, 1,
            "The correct answer is \"YES!\", becuase cats are way cooler than dogs.");
    Poll poll2 = new Poll("The earth is...", new String[] {"flat", "blue", "made out of cheese"}, 1,
        "The correct answer is \"blue\", as we know from the blue marble image.");
    LinkedList<Poll> polls = new LinkedList<>();
    polls.add(poll1);
    polls.add(poll2);
    Pack samplePack =
        new Pack("Sample Pack", "Two sample questions to illustrate how packs work.", "Max", polls);

    // Test persist
    packIO.persistPackToDisk(samplePack);

    // Test load
    Pack loadedPack = packIO.loadPackFromDisk("2023-07-19-sample-pack.json");
    System.out.println(loadedPack);
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