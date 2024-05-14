FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /workspace/app
COPY src src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
COPY  --from=build "/workspace/app/target/*.jar" "app.jar" 
ENTRYPOINT ["java", "-jar", "app.jar"]
