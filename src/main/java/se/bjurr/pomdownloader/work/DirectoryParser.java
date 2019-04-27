package se.bjurr.pomdownloader.work;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.MULTILINE;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectoryParser {
  private static Pattern AHREF_FOLDER_PATTERN =
      Pattern.compile("href=[\"']([^\"']+)/[\"']", CASE_INSENSITIVE | MULTILINE);
  private static Pattern AHREF_POM_PATTERN =
      Pattern.compile("href=[\"']([^\"']+\\.pom)[\"']", CASE_INSENSITIVE | MULTILINE);

  private final String url;

  private String content;

  public DirectoryParser(final String url) {
    this.url = url;
  }

  public List<String> getFolders() {
    getContent();
    return getAll(AHREF_FOLDER_PATTERN);
  }

  public List<String> getPomFiles() {
    getContent();
    return getAll(AHREF_POM_PATTERN);
  }

  private synchronized void getContent() {
    if (content == null) {
      this.content = UrlDownloader.readStringFromURL(url);
    }
  }

  private List<String> getAll(final Pattern pattern) {
    final List<String> list = new ArrayList<String>();
    getContent();
    final Matcher matcher = pattern.matcher(content);
    while (matcher.find()) {
      final String group = matcher.group(1).replaceAll("[:/]", "");
      if (!group.contains("..")) {
        list.add(group);
      }
    }
    return list;
  }
}
