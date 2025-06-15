FROM openjdk:17-jdk-slim AS build

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*


WORKDIR /app


COPY bd05 /app/bd05 

WORKDIR /app/bd05/bd

RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado de dentro da subpasta target
COPY --from=build /app/bd05/bd/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]