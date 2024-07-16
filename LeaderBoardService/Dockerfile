# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-jdk-slim

# Install necessary tools for network debugging
RUN apt-get update && apt-get install -y netcat telnet

# Add a volume pointing to /tmp (optional, depending on your application needs)
VOLUME /tmp

# Make port 8083 available to the world outside this container
EXPOSE 8083

# Copy the jar file from the target directory to the container
COPY target/leaderboard-0.0.1-SNAPSHOT.jar /app.jar

# Set the working directory
WORKDIR /

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
