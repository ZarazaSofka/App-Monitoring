FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app
COPY pom.xml ./

RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/app2.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
