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
            tools{
              jdk "JAVA_HOME_17"
            }
            steps{
              bat "./mvnw clean package"
            }   
        }
        
        
        stage('create image for docker') {
            agent linux
            steps{
              sh 'docker build -t spring:consumedbackend .'
            }
        }

        stage('Run the container'){
            steps{
              sh 'docker run -p 8080:8080 spring:consumedbackend'
            }
        }
    }
}