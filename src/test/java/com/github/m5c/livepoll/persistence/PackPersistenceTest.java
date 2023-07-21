package com.github.m5c.livepoll.persistence;

import com.github.m5c.livepoll.Pack;
import com.github.m5c.livepoll.PackMeta;
import com.github.m5c.livepoll.Poll;
import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import java.io.IOException;
import java.util.LinkedList;
import org.junit.Assert;
import org.junit.Test;

public class PackPersistenceTest {

  /**
   * Persists a provided test pack on disk.
   *
   * @param packPersistence as the pack object to write to disk.
   * @return absolute path to test file as string.
   * @throws IOException in case of a FileSystem access failure.
   */
  private String persistTestPack(PackPersistence packPersistence) throws IOException {

    // Create sample pack
    Poll poll1 =
        new Poll("Are cats cooler than dogs?", new String[] {"Yes", "YES!", "Absolutely"}, 1,
            "The correct answer is \"YES!\", becuase cats are way cooler than dogs.");
    Poll poll2 = new Poll("The earth is...", new String[] {"flat", "blue", "made out of cheese"}, 1,
        "The correct answer is \"blue\", as we know from the blue marble image.");
    LinkedList<Poll> polls = new LinkedList<>();
    polls.add(poll1);
    polls.add(poll2);
    PackMeta samplePackMeta =
        new PackMeta("Sample Pack", "Two sample questions to illustrate how packs work.", "Max");
    Pack samplePack = new Pack(samplePackMeta, polls);

    // Test persist
    return packPersistence.persistPackToDisk(samplePack);
  }

  @Test
  public void persistAndLoadTestPack() throws IOException, PackPersistenceException {

    PackPersistence packPersistence = new PackPersistence(System.getProperty("java.io.tmpdir"));

    // Create test pack on disk (test dir)
    String testPackDiskLocation = persistTestPack(packPersistence);

    // Test load
    String date = DateAndTopicIdGenerator.getFormattedDate();
    Pack loadedPack = packPersistence.loadPackFromDisk(date + "-sample-pack.json");
    System.out.println(loadedPack);

    // Verify content of deserialized pack
    Assert.assertEquals(
        "Amount of questions in deserialized Pack does not match the one of original.", 2,
        loadedPack.getQuestions().size());

    // Remove test pack to keep test directoy clean for subsequent test
    packPersistence.deletePack(testPackDiskLocation);
  }

  // TODO: CREATE NEW TEST FOR LAODING MAP WITH ALL METAS
}
