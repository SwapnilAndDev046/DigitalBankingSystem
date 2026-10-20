
# Build the Spring Boot application using Java 17
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy project files
COPY . .

# Build the executable Spring Boot JAR
RUN chmod +x mvnw && ./mvnw -B clean package -DskipTests

# Run the application using Java 17
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
