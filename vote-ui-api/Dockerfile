# Start with the Amazon Corretto 21 image
FROM amazoncorretto:21 as build

# Install Maven
RUN yum update -y && \
    yum install -y maven && \
    yum clean all

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Use Amazon Corretto 21 for the runtime
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/vote-0.0.1-SNAPSHOT.jar ./vote.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "vote.jar"]
