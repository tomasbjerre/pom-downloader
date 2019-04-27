package se.bjurr.pomdownloader.work;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import se.bjurr.pomdownloader.data.PomDownloaderArguments;

public class DirectoryParserTest {

  private static final String MIRROR = "https://repo1.maven.org/maven2";
  private static final String MIRROR_BT = "https://jcenter.bintray.com";
  private static final String FOLDER_WITHOUT_FILES = "/se/bjurr/jmib";
  private static final String FOLDER_WITH_FILES =
      "/se/bjurr/jmib/java-method-invocation-builder/1.3/";

  @Test
  public void testParseBintrayFolder() {
    final DirectoryParser dp = new DirectoryParser(MIRROR_BT + FOLDER_WITHOUT_FILES);
    assertThat(dp.getFolders())
        .containsOnly(
            "java-method-invocation-builder-annotations", "java-method-invocation-builder");
    assertThat(dp.getPomFiles()).isEmpty();
  }

  @Test
  public void testParseBintrayFolderWithFiles() {
    final List<String> mirrors = Arrays.asList(MIRROR_BT);
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final DirectoryParser dp =
        new DirectoryParser(PomDownloaderArguments.MIRROR + FOLDER_WITH_FILES);
    assertThat(dp.getFolders()).isEmpty();
    assertThat(dp.getPomFiles()).containsOnly("java-method-invocation-builder-1.3.pom");
  }

  @Test
  public void testParseCentralFolder() {
    final List<String> mirrors = Arrays.asList(MIRROR);
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final DirectoryParser dp =
        new DirectoryParser(PomDownloaderArguments.MIRROR + FOLDER_WITHOUT_FILES);
    assertThat(dp.getFolders())
        .containsOnly(
            "java-method-invocation-builder-annotations", "java-method-invocation-builder");
    assertThat(dp.getPomFiles()).isEmpty();
  }

  @Test
  public void testParseCentralFolderWithFiles() {
    final List<String> mirrors = Arrays.asList(MIRROR);
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final DirectoryParser dp =
        new DirectoryParser(PomDownloaderArguments.MIRROR + FOLDER_WITH_FILES);
    assertThat(dp.getFolders()).isEmpty();
    assertThat(dp.getPomFiles()).containsOnly("java-method-invocation-builder-1.3.pom");
  }

  @Test
  public void testParseCentralFolderWithDotsInVersion() {
    final List<String> mirrors = Arrays.asList(MIRROR);
    PomDownloaderArguments.INSTANCE = new PomDownloaderArguments(mirrors);
    final DirectoryParser dp =
        new DirectoryParser(MIRROR + "/se/bjurr/jmib/java-method-invocation-builder-annotations");
    assertThat(dp.getFolders()).containsOnly("1.0", "1.1", "1.2", "1.3", "1.4");
    assertThat(dp.getPomFiles()).isEmpty();
  }
}
