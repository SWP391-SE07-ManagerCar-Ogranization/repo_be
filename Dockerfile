# Use the official Gradle image to build the project
FROM gradle:7.5-jdk17 AS build
WORKDIR /workspace

# Copy the Gradle project files to the container
COPY build.gradle settings.gradle ./
COPY src ./src/

# Package the application to generate the JAR file
RUN gradle clean build -x test --info

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /workspace/build/libs/*.jar ./app.jar

# Expose the port the app runs on
EXPOSE 8080

# Set the entrypoint to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
