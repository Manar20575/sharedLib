#!/usr/bin/env groovy

#!/usr/bin/env groovy

def call() {
    node {
        def imageName = 'manar564/python'
        def tag = 'latest'
        
        stage('Checkout') {
            checkout scm
        }
        
        stage('Build Docker Image') {
            script {
                sh """
                    docker build -t ${imageName}:${tag} .
                """
            }
        }
        
        stage('Push to Registry') {
            script {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', 
                                                passwordVariable: 'DOCKER_PASSWORD', 
                                                usernameVariable: 'DOCKER_USERNAME')]) {
                    sh """
                        echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin
                        docker push ${imageName}:${tag}
                        docker logout
                    """
                }
            }
        }
    }
}
// def call(Map config = [:]) {
//     def dockerT1 = new org.iti.Docker(this)
//     pipeline {
//         agent {
//             label "java"
//         }
        
//         environment {
//             DOCKER_USER = credentials('dockerhub-user')
//             DOCKER_PASS = credentials('docker-pass')
//         }
        
//         parameters {
//             string(
//                 defaultValue: "${BUILD_NUMBER}", 
//                 description: 'Current Image Version', 
//                 name: 'VERSION'
//             )
//         }
        
//         stages {
//             stage("Build Docker Image") {
//                 steps {
//                     script {
//                         dockerT1.build("${DOCKER_USER}/python", "${params.VERSION}")
//                     }
//                 }
//             }
            
//             stage("Push Docker Image") {
//                 steps {
//                     script {
//                         dockerT1.login("${DOCKER_USER}", "${DOCKER_PASS}")
//                         dockerT1.push("${DOCKER_USER}/python", "${params.VERSION}")
//                     }            
//                 }
//             }
//         }
//     }
// }
