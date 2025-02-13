FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY keystore.p12 keystore.p12
ENV TZ=Asia/Seoul
EXPOSE 443
ENTRYPOINT ["java", "-jar", "/app.jar"]