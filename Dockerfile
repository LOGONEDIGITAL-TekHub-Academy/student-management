# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
#define few thhings
WORKDIR /app
COPY --from=builder /build/target/book-network-*.jar /app/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]