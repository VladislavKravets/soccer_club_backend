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

FROM gradle:7.2-jdk17 AS build
COPY . .
RUN gradle clean build -x test --no-daemon
FROM adoptopenjdk:17-jre-hotspot
COPY --from=build /home/gradle/build/libs/demo-1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

