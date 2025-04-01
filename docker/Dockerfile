FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim AS layer-extractor
WORKDIR /application
COPY --from=build /app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17-slim
WORKDIR /application
COPY --from=layer-extractor application/dependencies/ ./
COPY --from=layer-extractor application/snapshot-dependencies/ ./
COPY --from=layer-extractor application/spring-boot-loader/ ./
COPY --from=layer-extractor application/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher"]
