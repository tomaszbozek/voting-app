# Use an official Amazon Corretto 21 image
FROM amazoncorretto:21 AS build

# Install Maven 3.8.6 and necessary utilities
RUN yum update -y && \
    yum install -y wget tar gzip && \
    wget https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar xzf apache-maven-3.8.6-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.6 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    yum clean all

# Set environment variables for Maven
ENV MAVEN_HOME /opt/maven
ENV PATH ${MAVEN_HOME}/bin:${PATH}

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
COPY --from=build /app/target/vote-ui-api-0.0.1-SNAPSHOT.jar ./vote.jar

# Expose the port the application runs on
EXPOSE 8080

# Expose the debugging port
EXPOSE 5004

# Copy the entrypoint script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Command to run the application
ENTRYPOINT ["/entrypoint.sh"]