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

## Docker Registry repro
- https://github.com/wlanboy/CrudService/packages/278494

## Docker run
docker run --name crudservice -m 256M -d -p 8002:8002 -v /tmp:/tmp crudservice:latest

## Link to H2 web console
http://127.0.0.1:8002/h2

## Link to OpenApi doc
http://localhost:8002/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
