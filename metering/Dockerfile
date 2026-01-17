FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests -pl common -am
RUN mvn package -DskipTests -pl metering

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/metering/target/*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
