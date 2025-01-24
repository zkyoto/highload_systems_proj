#!/bin/bash

echo "⏳ Deploying namespace..."
minikube kubectl -- apply -f namespaces/default-namespace.yaml
minikube kubectl -- apply -f https://raw.githubusercontent.com/hazelcast/hazelcast/master/kubernetes-rbac.yaml

export HAZELCAST_VERSION=latest
minikube kubectl -- run hz-hazelcast-0 --image=hazelcast/hazelcast:$HAZELCAST_VERSION -l "role=hazelcast"
minikube kubectl -- run hz-hazelcast-1 --image=hazelcast/hazelcast:$HAZELCAST_VERSION -l "role=hazelcast"
minikube kubectl -- run hz-hazelcast-2 --image=hazelcast/hazelcast:$HAZELCAST_VERSION -l "role=hazelcast"

minikube kubectl -- create service clusterip hz-hazelcast --tcp=5701 -o yaml --dry-run=client | minikube kubectl -- set selector --local -f - "role=hazelcast" -o yaml | minikube kubectl -- create -f -


echo "⏳ Deploying PersistentVolumes and PersistentVolumeClaims..."
minikube kubectl -- apply -f persistent-volumes/

echo "⏳ Deploying PostgreSQL databases..."
minikube kubectl -- apply -f deployments/postgres-passport.yaml
minikube kubectl -- apply -f services/postgres-passport-svc.yaml
minikube kubectl -- apply -f deployments/postgres-interviews.yaml
minikube kubectl -- apply -f services/postgres-interviews-svc.yaml
minikube kubectl -- apply -f deployments/postgres-interviewers.yaml
minikube kubectl -- apply -f services/postgres-interviewers-svc.yaml
minikube kubectl -- apply -f deployments/postgres-interview-results.yaml
minikube kubectl -- apply -f services/postgres-interview-results-svc.yaml
minikube kubectl -- apply -f deployments/postgres-candidates.yaml
minikube kubectl -- apply -f services/postgres-candidates-svc.yaml
minikube kubectl -- apply -f deployments/postgres-feedbacks.yaml
minikube kubectl -- apply -f services/postgres-feedbacks-svc.yaml

echo "⏳ Deploying infrastructure services..."
minikube kubectl -- apply -f deployments/zookeeper.yaml
minikube kubectl -- apply -f services/zookeeper-svc.yaml
minikube kubectl -- apply -f deployments/kafka1.yaml
minikube kubectl -- apply -f services/kafka1-svc.yaml
minikube kubectl -- apply -f deployments/kafka2.yaml
minikube kubectl -- apply -f services/kafka2-svc.yaml
minikube kubectl -- apply -f deployments/kafka3.yaml
minikube kubectl -- apply -f services/kafka3-svc.yaml
minikube kubectl -- apply -f deployments/kafka-ui.yaml
minikube kubectl -- apply -f services/kafka-ui-svc.yaml

echo "⏳ Deploying discovery and config services..."
minikube kubectl -- apply -f deployments/eureka-server.yaml
minikube kubectl -- apply -f services/eureka-server-svc.yaml
minikube kubectl -- apply -f deployments/config-server.yaml
minikube kubectl -- apply -f services/config-server-svc.yaml

