# Multi-stage build za optimizaciju
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build aplikacije
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk

# Instaliraj curl za health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Kopiraj JAR file iz build stage
COPY --from=build /app/target/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
