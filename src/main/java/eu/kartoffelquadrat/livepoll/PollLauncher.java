package eu.kartoffelquadrat.livepoll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main launcher class.
 */
@SpringBootApplication
public class PollLauncher {

  // A dedicated directory in ram for all poll related data
  public static final String pollTmpDir =
      new File(System.getProperty("java.io.tmpdir"), "polldir").toString();

  /**
   * Launcher class for the Live Poll webapp.
   *
   * @param args optional command line arguments. None used so far.
   */
  public static void main(String[] args) throws IOException {

    // ensure pollTmpDir exists
    File tmpDir = new File(pollTmpDir);
    if (!tmpDir.exists()) {
      Files.createDirectories(tmpDir.toPath());
    }
    SpringApplication.run(PollLauncher.class, args);
  }

}
