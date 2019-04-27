package se.bjurr.pomdownloader.work;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PomDownloaderTest {

  @Test
  public void testPrintout() {
    final String actual = PomDownloader.getStatusString(123123345, 45621, 1234, 672);
    assertThat(actual)
        .isEqualTo(
            "Dirs queued: 45,621, Dirs done: 672, Poms queued: 123,123,345, Poms done: 1234");
  }
}
