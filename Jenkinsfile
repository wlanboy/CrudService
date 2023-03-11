pipeline {
  agent any
  tools { 
     jdk 'java11slave' 
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '1'))
  }
  parameters {
      booleanParam(defaultValue: false, description: 'Publish to DockerHub', name: 'PUBLISHIMAGE')
  }  
  environment {
    LOGSTASH = 'nuc:5044'
  }  
  stages {
    stage('Git') {
      steps {
        git(url: 'https://github.com/wlanboy/CrudService.git', branch: 'master')
      }
    }
    stage('Build') {
      steps {
        sh 'mvn clean package -Dmaven.test.failure.ignore=true'
      }
    }
    stage('Container') {
      steps {
        sh 'docker build -t crudservice:latest . --build-arg JAR_FILE=./target/crudservice-0.2.1-SNAPSHOT.jar'
      }
    }
    stage('Publish') {
      when { expression { params.PUBLISHIMAGE == true } }
      steps {
        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
          sh 'docker push wlanboy/crudservice:latest'
        }
      }
    }
  }
  post {
      cleanup {
          /* clean up our workspace */
          deleteDir()
          /* clean up tmp directory */
          dir("${workspace}@tmp") {
              deleteDir()
          }
          /* clean up script directory */
          dir("${workspace}@script") {
              deleteDir()
          }
      }
  }
}
