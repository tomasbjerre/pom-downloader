package se.bjurr.pomdownloader.data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import se.bjurr.pomdownloader.main.LogLevel;

public class PomDownloaderArguments {
  public static final String MIRROR = "<MIRROR>";
  public static PomDownloaderArguments INSTANCE;
  private static Random RANDOM = new Random(System.currentTimeMillis());
  private static Pattern GROUPID_PATTERN = Pattern.compile("^[^\\.].*[^\\.]$");

  private final List<String> mirrors;
  private final String groupId;
  private final String localMavenRepo;
  private final Integer pauseTime;
  private final Integer threads;
  private final LogLevel logLevel;

  public PomDownloaderArguments(
      final List<String> mirrors,
      final String groupId,
      final String localMavenRepo,
      final Integer pauseTime,
      final Integer threads,
      final LogLevel logLevel) {
    this.mirrors = getMirrors(mirrors);
    final Matcher matcher = GROUPID_PATTERN.matcher(groupId);
    if (!matcher.find()) {
      throw new RuntimeException(
          "Group is not valid! \"" + groupId + "\" " + GROUPID_PATTERN.pattern());
    }
    this.groupId = groupId;
    this.localMavenRepo = localMavenRepo;
    this.pauseTime = pauseTime;
    this.threads = threads;
    this.logLevel = logLevel;
  }

  public PomDownloaderArguments(final List<String> mirrors) {
    this.mirrors = getMirrors(mirrors);
    this.groupId = "groupId";
    this.localMavenRepo = "localMavenRepo";
    this.pauseTime = 10;
    this.threads = 1;
    this.logLevel = LogLevel.VERBOSE;
  }

  private List<String> getMirrors(final List<String> mirrors) {
    for (final String mirror : mirrors) {
      try {
        new URL(mirror);
      } catch (final MalformedURLException e) {
        throw new RuntimeException("Mirror is not valid URL! \"" + mirror + "\" " + e.getMessage());
      }
      if (mirror.endsWith("/")) {
        throw new RuntimeException("Mirror should not end with \"/\" \"" + mirror + "\"");
      }
    }
    return mirrors;
  }

  public LogLevel getLogLevel() {
    return logLevel;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getGroup() {
    return groupId.replace(".", "/");
  }

  public String getRootUrlToAnalyze() {
    return PomDownloaderArguments.MIRROR + "/" + getGroup();
  }

  public String getMirror() {
    return mirrors.get(RANDOM.nextInt(mirrors.size()));
  }

  public String getLocalMavenRepo() {
    return localMavenRepo;
  }

  public List<String> getMirrors() {
    return mirrors;
  }

  public Integer getPauseTime() {
    return pauseTime;
  }

  public Integer getThreads() {
    return threads;
  }

  public boolean isLogLevel(final LogLevel level) {
    return logLevel == level;
  }
}
