# CrudService
Spring Based CRUD Hateos Rest Service using Service Registry, Cloud Config and Zipkin

## Dependencies
At least: Java 8 and Maven 3.5

## Build CRUD Rest Service
mvn package -DskipTests=true

## Run CRUD Rest Service
### Environment variables
export DOCKERHOST=192.168.0.100

### Windows
java -jar target\crudservice-0.1.1-SNAPSHOT.jar

### Linux (service enabled)
./target/crudservice.jar start

## Docker build
docker build -t crudservice:latest . --build-arg JAR_FILE=./target/crudservice-0.1.1-SNAPSHOT.jar

## Docker run
docker run --name crudservice -m 256M -d -p 8002:8002 -v /tmp:/tmp -e DOCKERHOST=$DOCKERHOST crudservice:latest

## Link to H2 web console
http://127.0.0.1:8002/h2
