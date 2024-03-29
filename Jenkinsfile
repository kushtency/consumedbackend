pipeline {
    agent any
  

    stages {
        
        stage('Code checkout according to environment') {
            steps {
                script{
                  checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [[$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: 'consumedbackend']]]], userRemoteConfigs: [[credentialsId: 'gtihub-credentials', url: 'https://github.com/kushtency/consumedbackend.git']])
                }
            }
        } 
        stage("Build the code"){
            steps{
              sh "mvnw clean package"
            }   
        }
        
        
        // stage('create image for docker') {
        //     steps{
        //         sh 'docker build -t muskanchaurasia/mini-assignment  Mini-assignment/ '
        //     }
        // }

        // stage('Run the container'){
        //     steps{
        //         script{
        //             if(params.ENVIRONMENT=='DEVELOPMENT'){
        //                 def docker_container = sh(returnStdout: true, script: 'docker ps -a -f name="MiniAssignment" -q')
        //                 if(docker_container)
        //                     {
        //                 sh "docker stop ${docker_container}"
        //                 sh "docker rm --force ${docker_container}"
        //             }
        //             sh 'docker run -d --name MiniAssignment -p 8084:8080 muskanchaurasia/mini-assignment'
        //             }
        //             else if(params.ENVIRONMENT=='PRODUCTION'){
        //                 def docker_container = sh(returnStdout: true, script: 'docker ps -a -f name="MiniAssignment" -q')
        //                 if(docker_container)
        //                     {
        //                 sh "docker stop ${docker_container}"
        //                 sh "docker rm --force ${docker_container}"
        //             }
        //             sh 'docker run -d --name MiniAssignment -p 9090:8080 muskanchaurasia/mini-assignment'
        //             }
        //             else{
        //                 error('Kindly lookup the code')
        //             }
        //     }
        // }
    }
}