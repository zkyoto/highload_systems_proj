FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . .
RUN cd /app/common && ./install.sh
RUN cd /app/apps/interviewing-service && ./mvnw dependency:go-offline
CMD ["./apps/interviewing-service/mvnw", "spring-boot:run"]

