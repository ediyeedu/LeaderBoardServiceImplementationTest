# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8084 available to the world outside this container
EXPOSE 8084

# Copy the jar file from the target directory to the container
COPY target/PredictionService-0.0.1-SNAPSHOT.jar /app.jar

# Run the JAR fileclea

ENTRYPOINT ["java","-jar","/app.jar"]
