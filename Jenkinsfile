pipeline {
    agent any

    stages {
        
        stage('Code checkout according to environment') {
            steps {
                script{
                  checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'gtihub-credentials', url: 'https://github.com/kushtency/consumedbackend.git']])
                }
            }
        } 
        stage("Build the code"){
            steps{
              sh "chmod +x ./mvnw"
              sh "./mvnw clean package"
            }   
        }
        
        
        stage('create image for docker') {
            steps{
              sh 'docker build -t kushaagrsdocker/spring-consumedbackend:latest .'
            }
        }

        stage('Push the container'){
            steps{
              withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'docker-password', usernameVariable: 'docker-username')]) {
                sh "docker login -u ${env.docker-username} -p ${env.docker-password}"
                sh "docker push kushaagrsdocker/spring-consumedbackend:latest"
              }
            }
        }
    }
}