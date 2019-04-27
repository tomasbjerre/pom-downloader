package se.bjurr.pomdownloader.work;

import org.junit.Test;
import se.bjurr.pomdownloader.main.LogLevel;
import se.bjurr.pomdownloader.main.Main;

public class MainTest {

  @Test
  public void testMain() throws Exception {
    final String[] args = {
      "-g", "se.bjurr.jmib", //
      "-th", "1", //
      "-pt", "100", //
      "-ll", LogLevel.VERBOSE.name() //
    };
    Main.main(args);
  }

  @Test
  public void testHelp() throws Exception {
    final String[] args = {
      "-h" //
    };
    Main.main(args);
  }
}
