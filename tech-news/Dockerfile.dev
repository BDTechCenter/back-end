FROM openjdk:17-alpine

RUN mkdir -p /newsUpload

COPY target/tech-news-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]