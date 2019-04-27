package se.bjurr.pomdownloader.main;

import static se.softhouse.jargo.Arguments.helpArgument;
import static se.softhouse.jargo.Arguments.integerArgument;
import static se.softhouse.jargo.Arguments.stringArgument;
import static se.softhouse.jargo.CommandLineParser.withArguments;

import java.util.Arrays;
import java.util.List;
import se.bjurr.pomdownloader.data.PomDownloaderArguments;
import se.bjurr.pomdownloader.work.PomDownloader;
import se.softhouse.jargo.Argument;
import se.softhouse.jargo.ArgumentException;
import se.softhouse.jargo.ParsedArguments;

public class Main {

  private static final String USER_HOME = "<user home>";

  public static void main(final String args[]) throws Exception {
    final Argument<?> helpArgument = helpArgument("-h", "--help");

    final Argument<String> localMavenRepoArgument =
        stringArgument("-lm", "--local-maven") //
            .description("This is where it will store pom-files.") //
            .defaultValue(USER_HOME + "/.m2") //
            .build();
    final Argument<String> mirrorsArgument =
        stringArgument("-m", "--mirrors") //
            .description("Comma separated list of maven mirrors to use.") //
            .defaultValue(
                "https://repo1.maven.org/maven2,http://uk.maven.org/maven2,https://jcenter.bintray.com") //
            .build();
    final Argument<String> groupIdArgument =
        stringArgument("-g", "--groupid") //
            .description("Only consider artifacts within this group.") //
            .defaultValue("") //
            .build();
    final Argument<String> logLevelArgument =
        stringArgument("-ll", "--log-level") //
            .description(Arrays.asList(LogLevel.values()).toString()) //
            .defaultValue(LogLevel.INFO.name()) //
            .build();
    final Argument<Integer> threadsArgument =
        integerArgument("-th", "--threads") //
            .description("Number of threads to use.") //
            .defaultValue(1) //
            .build();
    final Argument<Integer> pauseTimeArgument =
        integerArgument("-pt", "--pause-time") //
            .description("Number of milliseconds to sleep before each request.") //
            .defaultValue(100) //
            .build();

    try {
      final ParsedArguments arg =
          withArguments(
                  helpArgument,
                  localMavenRepoArgument,
                  groupIdArgument,
                  threadsArgument,
                  pauseTimeArgument,
                  mirrorsArgument,
                  logLevelArgument) //
              .parse(args);

      final List<String> mirrors = Arrays.asList(arg.get(mirrorsArgument).split(","));
      final String groupId = arg.get(groupIdArgument);
      final String localMavenRepo =
          arg.get(localMavenRepoArgument).replace(USER_HOME, System.getProperty("user.home"));
      final Integer pauseTime = arg.get(pauseTimeArgument);
      final Integer threads = arg.get(threadsArgument);
      final LogLevel logLevel = LogLevel.valueOf(arg.get(logLevelArgument));

      System.out.println("Mirrors: " + mirrors);
      System.out.println("Group: " + (groupId.isEmpty() ? "Any" : groupId));
      System.out.println("Local Maven Repo: " + localMavenRepo);
      System.out.println("Pause Time: " + pauseTime);
      System.out.println("Threads: " + threads);
      System.out.println("Log level: " + logLevel);

      PomDownloaderArguments.INSTANCE =
          new PomDownloaderArguments(
              mirrors, groupId, localMavenRepo, pauseTime, threads, logLevel);

      final PomDownloader pomDownloader = new PomDownloader();

      pomDownloader.start();

    } catch (final ArgumentException exception) {
      System.out.println(exception.getMessageAndUsage());
      if (!Arrays.asList(args).contains("-h")) {
        System.exit(1);
      }
    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }
}
