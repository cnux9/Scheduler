#FROM ubuntu:latest
#LABEL authors="cnux9"
#
#ENTRYPOINT ["top", "-b"]
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]