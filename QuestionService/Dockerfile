# Use the official OpenJDK image from the Docker Hub
FROM openjdk:21-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 9099 available to the world outside this container
EXPOSE 9099

COPY target/questionService-0.0.1-SNAPSHOT.jar app.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]
