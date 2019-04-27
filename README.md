# Pom Downloader
[![Build Status](https://travis-ci.org/tomasbjerre/pom-downloader.svg?branch=master)](https://travis-ci.org/tomasbjerre/pom-downloader)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.pomdownloader/pom-downloader/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.pomdownloader/pom-downloader)
[![Bintray](https://api.bintray.com/packages/tomasbjerre/tomasbjerre/se.bjurr.pomdownloader%3Apom-downloader/images/download.svg) ](https://bintray.com/tomasbjerre/tomasbjerre/se.bjurr.pomdownloader%3Apom-downloader/_latestVersion)

This is a command line tool that downloads pom files from a remote Maven repository.

Intended to be used on a private Maven repository (like Nexus or Artifactory). As part of a set of tools to analyze dependencies between artifacts, see [Pom Dependency Analyzer](https://github.com/tomasbjerre/pom-dependency-analyzer).

You may try this on a public repository but be careful! Is is very likely a violation of its terms of service. See: https://central.sonatype.org/terms.html

Example, download all pom-files available in any group starting with `se.bjurr.violations`:
```shell
./pom-downloader -g se.bjurr.violations -pt 100 -th 2
```

# Usage

```shell
-g, --groupid <string>                     Only consider artifacts within this 
                                           group.
                                           <string>: any string
                                           Default: 
-h, --help <argument-to-print-help-for>    <argument-to-print-help-for>: an argument to print help for
                                           Default: If no specific parameter is given the whole usage text is given
-ll, --log-level <string>                  [INFO, VERBOSE]
                                           <string>: any string
                                           Default: INFO
-lm, --local-maven <string>                This is where it will store pom-files.
                                           <string>: any string
                                           Default: <user home>/.m2
-m, --mirrors <string>                     Comma separated list of maven 
                                           mirrors to use.
                                           <string>: any string
                                           Default: https://repo1.maven.org/maven2,http://uk.maven.org/maven2,https://jcenter.bintray.com
-pt, --pause-time <integer>                Number of milliseconds to sleep 
                                           before each request.
                                           <integer>: -2,147,483,648 to 2,147,483,647
                                           Default: 100
-th, --threads <integer>                   Number of threads to use.
                                           <integer>: -2,147,483,648 to 2,147,483,647
                                           Default: 1
```
