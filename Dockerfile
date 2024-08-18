# Use Maven to build the application
FROM maven:3.9.8-amazoncorretto-17-al2023 AS build
WORKDIR /app
#COPY .env .
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK to run the application
FROM openjdk:24-slim-bullseye
WORKDIR /app
COPY --from=build /app/target/blog-app-0.0.1-SNAPSHOT.jar app.jar
#COPY .env .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]