#!/bin/bash
set -e

echo "Starting Spring Boot application..."
echo "PORT: ${PORT:-8080}"
echo "SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE:-prod}"

# Find the JAR file
JAR_FILE=$(find target -name "*.jar" -type f | head -1)
echo "JAR file: $JAR_FILE"

# Start the application
exec java -Dserver.port=${PORT:-8080} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} $JAVA_OPTS -jar "$JAR_FILE"
