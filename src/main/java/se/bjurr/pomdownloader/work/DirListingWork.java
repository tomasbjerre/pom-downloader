package se.bjurr.pomdownloader.work;

public class DirListingWork {
  private final String url;
  private final String group;

  public DirListingWork(final String group, final String url) {
    this.group = group;
    this.url = url;
  }

  public Work perform() {
    final DirectoryParser dirListing = new DirectoryParser(url);

    return new Work(group, url, dirListing.getFolders(), dirListing.getPomFiles());
  }

  public String getGroup() {
    return group;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public String toString() {
    return "DirListingWork [url=" + url + ", group=" + group + "]";
  }
}
