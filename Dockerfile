FROM openjdk:17
ARG JAR_FILE=build/libs/be-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY keystore.p12 keystore.p12
ENV TZ=Asia/Seoul
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "/app.jar"]