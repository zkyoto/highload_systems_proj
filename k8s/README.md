# Kubernetes deploy guide
## Prerequisites
- Install minikube to your system
- Load all docker images to minikube using  `minikube image load ${IMAGE_NAME}`

## Start deployments and services
```
kubectl apply -f k8s/deployments/
kubectl apply -f k8s/services/
```

## Check status
```
kubectl get pods -n default
kubectl get services -n default
```
