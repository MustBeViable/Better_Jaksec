# Stage 1: build jar
FROM maven:3.9.12-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Stage 2: runtime
FROM eclipse-temurin:21-jdk
RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=builder /app/target/Better_Jaksec-1.0-SNAPSHOT.jar app.jar
COPY wait-for-db.sh /app/wait-for-db.sh
RUN chmod +x /app/wait-for-db.sh
CMD ["java","-jar","app.jar"]