# Use Maven to build the project
FROM maven:3.8.6-openjdk-21-slim AS build
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a separate image to run the application
FROM openjdk:21-slim
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/vote-ui-api-1.0.0.jar app.jar

# Expose the application's port
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
