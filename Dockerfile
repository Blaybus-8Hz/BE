FROM openjdk:17-jdk-slim

ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8443

ENTRYPOINT ["java", "-jar", "/app.jar"]