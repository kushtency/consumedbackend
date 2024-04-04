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
              sh "chmod +x ./mvn"
              sh "./mvnw clean package"
            }   
        }
        
    }
}
