FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests -pl common -am
RUN mvn package -DskipTests -pl wallet

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/wallet/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
