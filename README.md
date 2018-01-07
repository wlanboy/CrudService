# CrudService
Spring Based CRUD Rest Service using Service Registry, Cloud Config and Zipkin

## Dependencies
At least: Java 8 and Maven 3.5

## Build CRUD Rest Service
mvn package -DskipTests=true

## Run CRUD Rest Service
### Environment variables
#### EUREKA_ZONE 
Default value: http://127.0.0.1:8761/eureka/
Defining all available Eureka Instances.

### Windows
java -jar target\CrudService-0.0.1-SNAPSHOT.jar

### Linux (service enabled)
./target/CrudService.jar start

## Docker build
docker build -t crudservice:latest . --build-arg JAR_FILE=./target/CrudService-0.0.1-SNAPSHOT.jar

## Docker run
docker run --name crudservice -d -p 8002:8002 --link serviceregistry:serviceregistry -v /tmp:/tmp -e EUREKA_ZONE=$EUREKA_ZONE crudservice:latest
