package se.bjurr.pomdownloader.work;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import se.bjurr.pomdownloader.data.PomDownloaderArguments;

public class PomDownloaderWorkTest {

  private final String localMavenRepo = "~/.m2";

  @Test
  public void testCentral() {
    final List<String> mirrors = Arrays.asList("https://repo1.maven.org/maven2");
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final String url =
        PomDownloaderArguments.MIRROR
            + "/se/bjurr/violations/violations-lib/1.92/violations-lib-1.92.pom";
    final String group = "se/bjurr/violations";
    final PomFile actual = PomDownloaderWork.getPomFile(localMavenRepo, group, url);
    assertThat(actual.getFilePath().toString())
        .isEqualTo(
            "~/.m2/repository/se/bjurr/violations/violations-lib/1.92/violations-lib-1.92.pom");
  }

  @Test
  public void testBintray() {
    final List<String> mirrors = Arrays.asList("https://dl.bintray.com/tomasbjerre/tomasbjerre");
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final String url =
        PomDownloaderArguments.MIRROR
            + "/se/bjurr/violations/violations-lib/1.92/violations-lib-1.92.pom";
    final String group = "se/bjurr/violations";
    final PomFile actual = PomDownloaderWork.getPomFile(localMavenRepo, group, url);
    assertThat(actual.getFilePath().toString())
        .isEqualTo(
            "~/.m2/repository/se/bjurr/violations/violations-lib/1.92/violations-lib-1.92.pom");
  }
}
