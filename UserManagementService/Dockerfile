# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8090 available to the world outside this container
EXPOSE 8090

# Copy the jar file from the target directory to the container
COPY target/UserManagementService-0.0.1-SNAPSHOT.jar /app.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]
