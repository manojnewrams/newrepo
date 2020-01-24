FROM openjdk:8-jdk-alpine

LABEL maintainer="Team Nisum <@nisum.com>"
LABEL description="BackendApp"

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

RUN mkdir $HOME/app
#ARG JAR_FILE=build/libs/*.jar

#COPY ${JAR_FILE} $HOME/app/app.jar
COPY build/libs/* $HOME/app/
WORKDIR $HOME/app/
ENTRYPOINT ["java","-jar","h5api-0.0.1-SNAPSHOT.jar"]