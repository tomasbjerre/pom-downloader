package se.bjurr.pomdownloader.work;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.io.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PomDownloaderWork {
  private final String url;
  private final String group;

  public PomDownloaderWork(final String group, final String url) {
    this.url = url;
    this.group = group;
  }

  public void perform(final String localMavenRepo) {
    final PomFile pomFile = getPomFile(localMavenRepo, group, url);
    try {
      pomFile.getFilePath().toFile().getParentFile().mkdirs();
      Files.write(pomFile.getContent().getBytes(UTF_8), pomFile.getFilePath().toFile());
    } catch (final IOException e) {
      throw new RuntimeException(pomFile.getFilePath().toString(), e);
    }
  }

  public String getGroup() {
    return group;
  }

  public String getUrl() {
    return url;
  }

  static PomFile getPomFile(final String localMavenRepo, final String group, final String url) {
    final String pomContent = UrlDownloader.readStringFromURL(url);
    final String filename = url.substring(url.indexOf(group), url.length());
    final Path localMavenRepoPath = Paths.get(localMavenRepo, "repository", filename);
    return new PomFile(pomContent, localMavenRepoPath);
  }

  @Override
  public String toString() {
    return "PomDownloaderWork [url=" + url + ", group=" + group + "]";
  }
}
