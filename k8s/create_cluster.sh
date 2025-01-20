#!/bin/bash

if ! command -v minikube &> /dev/null; then
  echo "❌ Minikube not installed"
  exit 1
fi

minikube start --cpus=28 --memory=16384 --disk-size=100g --driver=docker

if [ $? -eq 0 ]; then
  echo "✅ Minikube created"
else
  echo "❌ Cannot create Minikube"
  exit 1
fi

