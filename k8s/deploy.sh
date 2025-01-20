#!/bin/bash

# Function to deploy services in order
function deploy_services() {
  echo "🚀 Starting deployment of all services..."

  # Step 1: Deploy Namespaces (if needed)
  echo "⏳ Deploying namespace..."
  minikube kubectl -- apply -f k8s/namespaces/default-namespace.yaml

  # Step 2: Deploy Persistent Volumes and Claims
  echo "⏳ Deploying PersistentVolumes and PersistentVolumeClaims..."
  minikube kubectl -- apply -f k8s/persistent-volumes/

  # Step 3: Deploy PostgreSQL Databases
  echo "⏳ Deploying PostgreSQL databases..."
  minikube kubectl -- apply -f k8s/deployments/postgres-passport.yaml
  minikube kubectl -- apply -f k8s/services/postgres-passport-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/postgres-interviews.yaml
  minikube kubectl -- apply -f k8s/services/postgres-interviews-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/postgres-interviewers.yaml
  minikube kubectl -- apply -f k8s/services/postgres-interviewers-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/postgres-interview-results.yaml
  minikube kubectl -- apply -f k8s/services/postgres-interview-results-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/postgres-candidates.yaml
  minikube kubectl -- apply -f k8s/services/postgres-candidates-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/postgres-feedbacks.yaml
  minikube kubectl -- apply -f k8s/services/postgres-feedbacks-svc.yaml

  # Step 4: Deploy Infrastructure Services (Zookeeper, Kafka, Kafka UI)
  echo "⏳ Deploying infrastructure services..."
  minikube kubectl -- apply -f k8s/deployments/zookeeper.yaml
  minikube kubectl -- apply -f k8s/services/zookeeper-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/kafka1.yaml
  minikube kubectl -- apply -f k8s/services/kafka1-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/kafka2.yaml
  minikube kubectl -- apply -f k8s/services/kafka2-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/kafka3.yaml
  minikube kubectl -- apply -f k8s/services/kafka3-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/kafka-ui.yaml
  minikube kubectl -- apply -f k8s/services/kafka-ui-svc.yaml

  # Step 5: Deploy Discovery and Config Services
  echo "⏳ Deploying discovery and config services..."
  minikube kubectl -- apply -f k8s/deployments/eureka-server.yaml
  minikube kubectl -- apply -f k8s/services/eureka-server-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/config-server.yaml
  minikube kubectl -- apply -f k8s/services/config-server-svc.yaml

  # Step 6: Deploy Application Services (API Gateway, Microservices)
  echo "⏳ Deploying application services..."
  minikube kubectl -- apply -f k8s/deployments/api-gateway.yaml
  minikube kubectl -- apply -f k8s/services/api-gateway-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/file-manager.yaml
  minikube kubectl -- apply -f k8s/services/file-manager-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/interviews.yaml
  minikube kubectl -- apply -f k8s/services/interviews-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/interviewers.yaml
  minikube kubectl -- apply -f k8s/services/interviewers-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/interview-results.yaml
  minikube kubectl -- apply -f k8s/services/interview-results-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/candidates.yaml
  minikube kubectl -- apply -f k8s/services/candidates-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/feedbacks.yaml
  minikube kubectl -- apply -f k8s/services/feedbacks-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/authorizator.yaml
  minikube kubectl -- apply -f k8s/services/authorizator-svc.yaml
  minikube kubectl -- apply -f k8s/deployments/passport.yaml
  minikube kubectl -- apply -f k8s/services/passport-svc.yaml

  echo "✅ All services have been successfully deployed!"
}

# Execute the deployment function
deploy_services

