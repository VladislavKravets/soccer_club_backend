#FROM azul/zulu-openjdk:17-latest
#
#ARG JAR_FILE=target/*.jar
#COPY build/libs/*.jar soccer_club_backend-0.0.1-SNAPSHOT.jar
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","/soccer_club_backend-0.0.1-SNAPSHOT.jar"]
#
#FROM ubuntu:latest AS build
#
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
#COPY . .
#
#RUN chmod -x gradlew
#
#RUN ./gradlew bootJar --no-daemon
#
#FROM openjdk:17-jdk-slim
#
#EXPOSE 8080
#
#COPY --from=build /build/libs/demo-1.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

#
# Build stage
#
FROM gradle:jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

LABEL org.name="hezf"
# Package stage
#
FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /home/gradle/src/build/libs/demo-1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

