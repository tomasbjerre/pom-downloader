package se.bjurr.pomdownloader.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import se.bjurr.pomdownloader.main.LogLevel;

public class PomDownloaderArgumentsTest {

  private final List<String> mirrors = Arrays.asList("https://repo1.maven.org/maven2");
  private final String groupId = "se.bjurr.violations";
  private final String localMavenRepo = "~/.m2";
  private final Integer pauseTime = 1;
  private final Integer threads = 2;

  @Test
  public void testGroupId() {
    final PomDownloaderArguments pomDownloader =
        new PomDownloaderArguments(
            mirrors, groupId, localMavenRepo, pauseTime, threads, LogLevel.VERBOSE);
    assertThat(pomDownloader.getRootUrlToAnalyze()).isEqualTo("<MIRROR>/se/bjurr/violations");
  }
}
