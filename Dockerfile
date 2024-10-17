FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . .
RUN cd /app/common && ./install.sh
RUN cd /app/apps/interviewing-service && ./mvnw dependency:go-offline
WORKDIR /app/apps/interviewing-service 
CMD ["./mvnw", "spring-boot:run"]

