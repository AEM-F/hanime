#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER aem

# Add the application's jar to the container
COPY target/hanime-discovery-0.0.1-SNAPSHOT.jar hanime-discovery-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/hanime-discovery-0.0.1-SNAPSHOT.jar"]
