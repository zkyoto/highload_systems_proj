#!/bin/bash

echo "⏳ Deploying application services..."
minikube kubectl -- apply -f deployments/api-gateway.yaml
minikube kubectl -- apply -f services/api-gateway-svc.yaml
minikube kubectl -- apply -f deployments/file-manager.yaml
minikube kubectl -- apply -f services/file-manager-svc.yaml
minikube kubectl -- apply -f deployments/interviews.yaml
minikube kubectl -- apply -f services/interviews-svc.yaml
minikube kubectl -- apply -f deployments/interviewers.yaml
minikube kubectl -- apply -f services/interviewers-svc.yaml
minikube kubectl -- apply -f deployments/interview-results.yaml
minikube kubectl -- apply -f services/interview-results-svc.yaml
minikube kubectl -- apply -f deployments/candidates.yaml
minikube kubectl -- apply -f services/candidates-svc.yaml
minikube kubectl -- apply -f deployments/feedbacks.yaml
minikube kubectl -- apply -f services/feedbacks-svc.yaml
minikube kubectl -- apply -f deployments/authorizator.yaml
minikube kubectl -- apply -f services/authorizator-svc.yaml
minikube kubectl -- apply -f deployments/passport.yaml
minikube kubectl -- apply -f services/passport-svc.yaml

minikube kubectl -- apply -f deployments/ingress.yaml
