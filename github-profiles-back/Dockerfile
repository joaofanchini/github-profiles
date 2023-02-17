# Build stage
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

# Package stage
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/github-profiles-back-0.0.1.jar
WORKDIR /opt/app
MAINTAINER joaofanchini@gmail.com
COPY --from=build /app/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]