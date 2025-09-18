#!/bin/bash

# Set JAVA_HOME if not already set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
fi

# Set Maven options
export MAVEN_OPTS="-Xmx1024m"

# Start the application
exec java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar
