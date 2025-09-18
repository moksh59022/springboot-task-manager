#!/bin/bash
set -e

echo "Setting up Java environment..."

# Find Java installation
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=$(dirname $(dirname $(which java)))
fi

echo "JAVA_HOME: $JAVA_HOME"
echo "Java version:"
java -version

# Make mvnw executable
chmod +x ./mvnw

echo "Building application..."
./mvnw clean package -DskipTests -Pproduction

echo "Build completed successfully!"
ls -la target/
