![Java CI with Maven](https://github.com/wlanboy/CrudService/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master) ![Docker build and publish image](https://github.com/wlanboy/CrudService/workflows/Docker%20build%20and%20publish%20image/badge.svg)

# CrudService
Spring Based CRUD Hateos Rest Service using Cloud Config and H2

## Dependencies
At least: Java 11 and Maven 3.5

## Build 
mvn package -DskipTests=true

## Run 
### Environment variables

### Windows
java -jar target\crudservice-0.1.1-SNAPSHOT.jar

### Linux (service enabled)
./target/crudservice-0.1.1-SNAPSHOT.jar start

## Docker build
docker build -t crudservice:latest . --build-arg JAR_FILE=./target/crudservice-0.1.1-SNAPSHOT.jar

## Docker publish to github registry
- docker tag serviceconfig:latest docker.pkg.github.com/wlanboy/crudservice/crudservice:latest
- docker push docker.pkg.github.com/wlanboy/crudservice/crudservice:latest

## Docker Hub
- https://hub.docker.com/repository/docker/wlanboy/crudservice

## Docker Registry repro
- https://github.com/wlanboy/CrudService/packages/278494

## Docker run
docker run --name crudservice -m 256M -d -p 8002:8002 -v /tmp:/tmp crudservice:latest

## Kubernets deployment

### Prepare
```
cd ~
git clone https://github.com/wlanboy/CrudService.git
```

### check you local kubectl
```
kubectl cluster-info
kubectl get pods --all-namespaces
```

### deploy service on new namespace
```
cd ~/CrudService
kubectl create namespace crud
kubectl apply -f crudservice-deployment.yaml
kubectl apply -f crudservice-service.yaml
kubectl get pods -n crud -o wide
```

### check deployment and service
```
kubectl describe deployments -n crud crudservice 
kubectl describe services -n crud crudservice-service
```

### expose service and get node port
```
kubectl expose deployment -n crud crudservice --type=NodePort --name=crudservice-serviceexternal --port 8002
kubectl describe services -n crud crudservice-serviceexternal 
```
Result:
```
Name:                     crudservice-serviceexternal
Namespace:                crud
Labels:                   app=crudservice
Annotations:              <none>
Selector:                 app=crudservice
Type:                     NodePort
IP Family Policy:         SingleStack
IP Families:              IPv4
IP:                       10.108.40.138
IPs:                      10.108.40.138
Port:                     <unset>  8002/TCP
TargetPort:               8002/TCP
NodePort:                 <unset>  30411/TCP  <--- THIS IS THE PORT WE NEED
Endpoints:                10.10.0.6:8002
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
```

###  call crudservice
* curl http://192.168.56.100:30411/actuator/health
* Result
```
{"status":"UP","groups":["liveness","readiness"]}
```

### deploy ingress service for crudservice
```
kubectl apply -f crudservice-ingress.yml
kubectl describe ingress crudservice-ingress -n crud
```

## Link to H2 web console
http://127.0.0.1:8002/h2

## Link to OpenApi doc
http://localhost:8002/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
