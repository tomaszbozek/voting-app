#!/bin/sh

if [ "$JAVA_DEBUG" = "true" ]; then
    exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar vote.jar
else
    exec java -jar vote.jar
fi
