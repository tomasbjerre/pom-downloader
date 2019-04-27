package se.bjurr.pomdownloader.work;

import java.util.List;
import java.util.stream.Collectors;

public class Work {
  private final List<String> folders;
  private final List<String> pomFiles;
  private final String group;
  private final String url;

  public Work(
      final String group,
      final String url,
      final List<String> folders,
      final List<String> pomFiles) {
    this.group = group;
    this.url = url;
    this.folders = folders;
    this.pomFiles = pomFiles;
  }

  public List<DirListingWork> getFolders() {
    return folders
        .stream()
        .map((it) -> new DirListingWork(group + "/" + it, url + "/" + it))
        .collect(Collectors.toList());
  }

  public List<PomDownloaderWork> getPomFiles() {
    return pomFiles
        .stream()
        .map((it) -> new PomDownloaderWork(group + "/" + it, url + "/" + it))
        .collect(Collectors.toList());
  }
}
