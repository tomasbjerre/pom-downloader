package se.bjurr.pomdownloader.work;

import java.nio.file.Path;

public class PomFile {
  private final String content;
  private final Path filePath;

  public PomFile(final String content, final Path filePath) {
    this.content = content;
    this.filePath = filePath;
  }

  public String getContent() {
    return content;
  }

  public Path getFilePath() {
    return filePath;
  }
}
