FROM maven:3.8.1-openjdk-17 AS build

WORKDIR /app
COPY pom.xml /app/
COPY src /app/src/
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=build /app/target/ms-event-manager-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
