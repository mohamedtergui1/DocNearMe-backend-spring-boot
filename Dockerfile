# Use a base image with Maven and JDK for building
FROM maven:3.8-openjdk-17-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven POM file and the source code to the container
COPY pom.xml ./pom.xml
COPY src ./src

# Run Maven to build the project and create a JAR file
RUN mvn clean package -DskipTests

# Use the official OpenJDK 17 runtime image for running the app
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Set default environment variables
ENV EMAIL_USERNAME=default@example.com \
    EMAIL_PASSWORD=defaultpassword \
    SECRET_KEY=defaultsecretkey \
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/docnearme \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=postgres

# Copy the JAR file from the build stage
COPY --from=builder /app/target/DocNearMe-0.0.1.jar /app/app.jar

# Expose port 8080 to be able to access the application externally
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]