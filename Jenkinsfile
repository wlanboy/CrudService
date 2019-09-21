pipeline {
  agent any
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
        deleteDir()
    }
  }  
}
