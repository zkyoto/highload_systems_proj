#!/bin/bash

IMAGES=(
  "kyoto67/highload/interviewing-service/interviewers:latest"
  "kyoto67/highload/interviewing-service/interview-results:latest"
  "kyoto67/highload/interviewing-service/interviews:latest"
  "kyoto67/highload/interviewing-service/candidates:latest"
  "kyoto67/highload/interviewing-service/feedbacks:latest"
  "kyoto67/highload/file-manager:latest"
  "kyoto67/highload/authorizator:latest"
  "kyoto67/highload/api-gateway:latest"
  "kyoto67/highload/passport:latest"
  "kyoto67/highload/config-server:latest"
  "kyoto67/highload/eureka-server:latest"
  "kyoto67/highload/interviewers-postgres:latest"
  "kyoto67/highload/passport-postgres:latest"
  "kyoto67/highload/candidates-postgres:latest"
  "kyoto67/highload/interview-results-postgres:latest"
  "kyoto67/highload/interviews-postgres:latest"
  "kyoto67/highload/feedbacks-postgres:latest"
)

if ! command -v minikube &> /dev/null; then
  echo "❌ Minikube is not installed. Please install it and try again."
  exit 1
fi

if ! minikube status &> /dev/null; then
  echo "❌ Minikube cluster is not running. Please start it and try again."
  exit 1
fi

echo "🚀 Loading Docker images into Minikube..."
for IMAGE in "${IMAGES[@]}"; do
  echo "⏳ Loading image $IMAGE..."
  minikube image load "$IMAGE" --overwrite=true
  if [ $? -eq 0 ]; then
    echo "✅ Image $IMAGE successfully loaded!"
  else
    echo "❌ Failed to load image $IMAGE."
  fi
done

echo "✅ All images successfully loaded into Minikube!"


