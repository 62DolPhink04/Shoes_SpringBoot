# Build stage (Giữ nguyên dòng này nếu nó vẫn chạy được, hoặc đổi sang maven:3.9-eclipse-temurin-11)
FROM maven:3.8.4-openjdk-11-slim AS build
COPY . .
RUN mvn clean package -DskipTests

# Run stage (SỬA DÒNG NÀY)
# Thay vì dùng openjdk:11-jre-slim (đã chết), hãy dùng dòng dưới:
FROM eclipse-temurin:11-jre
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]