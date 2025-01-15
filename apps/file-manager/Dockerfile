FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . .
RUN cd /app && ./mvnw dependency:go-offline
CMD ["./mvnw", "spring-boot:run"]