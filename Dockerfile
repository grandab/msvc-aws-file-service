# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-alpine

LABEL authors="esten"

ARG JAR_FILE=/app/target/*.jar


# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/file-service-0.0.1-SNAPSHOT.jar file-service-0.0.1-SNAPSHOT.jar

# Expose the port the app runs on
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "file-service-0.0.1-SNAPSHOT.jar"]