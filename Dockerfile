FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY bd05/bd/pom.xml bd05/bd/
COPY bd05/bd/src bd05/bd/src

COPY bd05/bd/ .

WORKDIR /app/bd05/bd

RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/bd05/bd/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]