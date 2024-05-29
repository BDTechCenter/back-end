FROM maven:3.9.6-amazoncorretto-17-debian-bookworm as BUILD

COPY settings.xml /root/.m2/settings.xml

ENV http_proxy=http://172.0.0.1:3128
ENV https_proxy=http://172.0.0.1:3128

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app

RUN mkdir -p ./uploads

COPY --from=BUILD /app/target/image-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]