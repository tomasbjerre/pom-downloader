package se.bjurr.pomdownloader.work;

import static java.util.stream.Collectors.joining;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import se.bjurr.pomdownloader.data.PomDownloaderArguments;
import se.bjurr.pomdownloader.main.LogLevel;

public class PomDownloader {

  private int pomsDownloaded = 0;
  private final List<PomDownloaderWork> pomDownloadWorkList =
      Collections.synchronizedList(new ArrayList<PomDownloaderWork>());
  private int dirsListed = 0;
  private final List<DirListingWork> dirListingWorkList =
      Collections.synchronizedList(new ArrayList<DirListingWork>());
  private Instant started;

  public PomDownloader() {}

  public void start() {
    started = Instant.now();
    this.dirListingWorkList.add(
        new DirListingWork(
            PomDownloaderArguments.INSTANCE.getGroup(),
            PomDownloaderArguments.INSTANCE.getRootUrlToAnalyze()));
    do {
      if (PomDownloaderArguments.INSTANCE.isLogLevel(LogLevel.VERBOSE)) {
        System.out.println();
        System.out.println();
        System.out.println(
            "Dirs in queue:\n"
                + dirListingWorkList.stream().map((it) -> it.getUrl()).collect(joining("\n")));
        System.out.println(
            "Poms in queue:\n"
                + pomDownloadWorkList.stream().map((it) -> it.getUrl()).collect(joining("\n")));
        System.out.println();
        System.out.println();
      }
      final ExecutorService executorService =
          Executors.newFixedThreadPool(PomDownloaderArguments.INSTANCE.getThreads());
      for (final PomDownloaderWork work : new ArrayList<>(pomDownloadWorkList)) {
        executorService.execute(
            () -> {
              sleep();
              work.perform(PomDownloaderArguments.INSTANCE.getLocalMavenRepo());
              pomDownloadWorkList.remove(work);
              pomsDownloaded++;
              printStatus();
            });
      }
      for (final DirListingWork work : new ArrayList<>(dirListingWorkList)) {
        executorService.execute(
            () -> {
              sleep();
              final Work moreWork = work.perform();
              dirListingWorkList.remove(work);
              pomDownloadWorkList.addAll(moreWork.getPomFiles());
              dirListingWorkList.addAll(moreWork.getFolders());
              dirsListed++;
              printStatus();
            });
      }
      try {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
      } catch (final InterruptedException e) {
        throw new RuntimeException("Failed to wait for threads to finnish.", e);
      }
      printStatus();
    } while (!pomDownloadWorkList.isEmpty() || !dirListingWorkList.isEmpty());
  }

  private void sleep() {
    try {
      Thread.sleep(PomDownloaderArguments.INSTANCE.getPauseTime());
    } catch (final InterruptedException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private void printStatus() {
    final String printout =
        getStatusString(
            pomDownloadWorkList.size(), dirListingWorkList.size(), pomsDownloaded, dirsListed);
    final String minutes =
        NumberFormat.getNumberInstance(Locale.US)
            .format(Duration.between(started, Instant.now()).toMinutes());
    System.out.println(printout + " after " + minutes + "m");
  }

  static String getStatusString(
      final int pomDownloadSize,
      final int dirListingSize,
      final int pomsDownloaded,
      final int dirsListed) {
    final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    final String printout =
        "Dirs queued: "
            + numberFormat.format(dirListingSize)
            + ", Dirs done: "
            + dirsListed
            + ", Poms queued: "
            + numberFormat.format(pomDownloadSize)
            + ", Poms done: "
            + pomsDownloaded;
    return printout;
  }
}
