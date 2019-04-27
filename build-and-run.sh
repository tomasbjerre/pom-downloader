#!/bin/bash

#
# I use this as a cron job to periodically analyze dependencies.
#

POM_FOLDER=~/.m2/repository/se/bjurr

./gradlew clean build \
  && ./gradlew run --args="-g se.bjurr -pt 1000 -th 1"
