package com.github.m5c.livepoll.persistence;

import com.github.m5c.livepoll.persistene.FileSystemInitializer;
import com.github.m5c.livepoll.persistene.PackIO;
import com.github.m5c.livepoll.pollutils.DateAndTopicIdGenerator;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class FileSystemInitializerTest {

  @Test
  public void testStructureCreationInRam() throws IOException {

    // Set up mock basedir location
    String testBaseDir = System.getProperty("java.io.tmpdir").toString()+"/.dummybasedir";
    File testBaseDirFile = new File(testBaseDir);
    System.out.println(testBaseDirFile);

    // Let initlialiser create required strucutre in test tmp dir]
    FileSystemInitializer initializer =
        new FileSystemInitializer(new DateAndTopicIdGenerator(), new PackIO(testBaseDir), testBaseDir);
    initializer.ensureBaseDirIsReady();

    // Verify the created structure is sane
    Assert.assertTrue("Initilizer did not create required basedir.", testBaseDirFile.exists());
    Assert.assertTrue("Basedir created by initilizer is not a directory.", FileUtils.isDirectory(testBaseDirFile));

    // Erase the created test structure, so this does not interfere woth other / later tests.
    FileUtils.deleteDirectory(testBaseDirFile);
  }
}
