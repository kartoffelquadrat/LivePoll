package eu.kartoffelquadrat.livepoll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Main launcher class.
 */
@SpringBootApplication
public class PollLauncher {

  /**
   * Launcher class for the Live Poll webapp.
   *
   * @param args optional command line arguments. None used so far.
   */
  public static void main(String[] args) {
    SpringApplication.run(PollLauncher.class, args);
  }

}
