FROM maven:3.8.3-openjdk-17

WORKDIR /app
COPY . .
RUN cd /app/common && ./install.sh
RUN cd /app/apps/api-gateway && ./mvnw dependency:go-offline
RUN cd /app/apps/authorizator && ./mvnw dependency:go-offline
RUN cd /app/apps/interviewing-service && ./mvnw dependency:go-offline
RUN cd /app/service-discovery/eureka-server && ./mvnw dependency:go-offline
RUN cd /app/service-discovery/config-server && ./mvnw dependency:go-offline