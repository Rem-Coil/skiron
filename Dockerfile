FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY build/libs/skiron-0.1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]