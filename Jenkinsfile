pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '7'))
  }
  environment {
    LOGSTASH = "nuc:5044"
  }  
  stages {
    stage('Git') {
      steps {
        git(url: 'https://github.com/wlanboy/CrudService.git', branch: 'master')
      }
    }
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Publish') {
      steps {
        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
          sh 'docker push wlanboy/crudservice:latest'
        }
      }
    }
  }
}