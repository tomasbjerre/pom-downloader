package se.bjurr.pomdownloader.work;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import se.bjurr.pomdownloader.data.PomDownloaderArguments;
import se.bjurr.pomdownloader.main.LogLevel;

public class UrlDownloader {
  public static String readStringFromURL(final String requestURL) {
    Exception ex = null;
    final List<String> shuffled = new ArrayList<>(PomDownloaderArguments.INSTANCE.getMirrors());
    Collections.shuffle(shuffled);
    for (final String mirror : shuffled) {
      try {
        return doDownload(mirror, requestURL);
      } catch (final Exception e) {
        if (shuffled.lastIndexOf(mirror) != shuffled.size() - 1) {
          System.out.println("Retrying " + requestURL + " " + e.getMessage() + "... ");
        }
        ex = e;
      }
    }
    throw new RuntimeException(requestURL, ex);
  }

  private static String doDownload(final String mirror, String requestURL)
      throws MalformedURLException, IOException {
    requestURL = requestURL.replace(PomDownloaderArguments.MIRROR, mirror);
    if (PomDownloaderArguments.INSTANCE.isLogLevel(LogLevel.VERBOSE)) {
      System.out.println("<<< " + requestURL);
    }
    try (Scanner scanner =
        new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
      scanner.useDelimiter("\\A");
      return scanner.hasNext() ? scanner.next() : "";
    }
  }
}
