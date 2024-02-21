FROM openjdk:17-alpine

COPY target/service-discovery-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8765

ENTRYPOINT ["java", "-jar", "app.jar"]