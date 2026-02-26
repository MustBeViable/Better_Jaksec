# -------- Build Stage --------
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom first (better layer caching)
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy source
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:17-jre
WORKDIR /app
# Install MariaDB client
RUN apt-get update && apt-get install -y mariadb-client && rm -rf /var/lib/apt/lists/*
# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for-db.sh wait-for-db.sh
RUN chmod +x wait-for-db.sh

# Expose Spring port
EXPOSE 8080

# Run app
ENTRYPOINT ["./wait-for-db.sh", "db", "java", "-jar", "app.jar"]
