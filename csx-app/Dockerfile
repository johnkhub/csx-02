#FROM maven:3.6.1-jdk-8-slim AS build
FROM maven:3.8.6-jdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -f pom.xml clean package -DskipTests=true

#FROM openjdk:8-alpine
FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-debian-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]