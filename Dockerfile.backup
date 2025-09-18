# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Install curl for potential debugging
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper files first
COPY .mvn .mvn
COPY mvnw mvnw.cmd pom.xml ./

# Make mvnw executable and verify wrapper
RUN chmod +x mvnw && \
    ls -la .mvn/wrapper/ && \
    ./mvnw --version

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -Pproduction && \
    ls -la target/

# Copy and setup entrypoint script
COPY docker-entrypoint.sh ./
RUN chmod +x docker-entrypoint.sh

# Expose port
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application - Railway will inject PORT automatically
CMD ["java", "-jar", "target/task-management-system-0.0.1-SNAPSHOT.jar"]
