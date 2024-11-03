# Use JDK 17 as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY build/libs/backend-test.jar /app/backend-test.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/backend-test.jar"]