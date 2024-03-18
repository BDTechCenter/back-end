FROM openjdk:17-alpine

COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

ENV http_proxy=http://172.0.0.1:3128
ENV https_proxy=http://172.0.0.1:3128

ENTRYPOINT ["java", "-jar", "app.jar"]