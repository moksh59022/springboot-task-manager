#!/bin/bash
set -e

echo "Starting Spring Boot Task Manager..."

# Set default values
export PORT=${PORT:-8080}
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}

echo "PORT: $PORT"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"

# Use the exact JAR file name
JAR_FILE="target/task-management-system-0.0.1-SNAPSHOT.jar"

if [ -f "$JAR_FILE" ]; then
    echo "Found JAR file: $JAR_FILE"
    exec java -Dserver.port=$PORT -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE $JAVA_OPTS -jar "$JAR_FILE"
else
    echo "JAR file not found: $JAR_FILE"
    echo "Available files in target:"
    ls -la target/
    exit 1
fi
