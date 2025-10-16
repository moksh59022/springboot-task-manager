# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace/app

# Cache Maven dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the application
COPY src src/
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod \
    TZ=UTC \
    LANG=C.UTF-8 \
    JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Copy the JAR file
COPY --from=build /workspace/app/target/*.jar app.jar

# Create a non-root user and switch to it
RUN addgroup --system --gid 1001 appuser && \
    adduser --system --uid 1001 --gid 1001 appuser && \
    chown -R appuser:appuser /app
USER 1001

# Expose the port (for documentation only, doesn't actually publish the port)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "$JAVA_OPTS", "-jar", "app.jar", "--server.port=${PORT:-8080}"]