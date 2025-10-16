# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace/app

# Copy only the files needed for dependency resolution
COPY pom.xml .
COPY src src/

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Copy the JAR file from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 10000

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=${PORT}"]
