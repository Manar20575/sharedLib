#!/usr/bin/env groovy
def call() {
    def dockerT1 = new org.iti.Docker(this)
    pipeline {
        agent {
            label "java"
        }
        
        environment {
            DOCKER_USER = credentials('dockerhub-user')
            DOCKER_PASS = credentials('docker-pass')
        }
        
        parameters {
            string(
                defaultValue: "${BUILD_NUMBER}", 
                description: 'Current Image Version', 
                name: 'VERSION'
            )
        }
        
        stages {
            stage("Build Docker Image") {
                steps {
                    script {
                        dockerT1.build("${DOCKER_USER}/python", "${params.VERSION}")
                    }
                }
            }
            
            stage("Push Docker Image") {
                steps {
                    script {
                        dockerT1.login("${DOCKER_USER}", "${DOCKER_PASS}")
                        dockerT1.push("${DOCKER_USER}/python", "${params.VERSION}")
                    }            
                }
            }
        }
    }
}
// def call() {
//     pipeline {
//         agent any
        
//         stages {
//             stage('Checkout') {
//                 steps {
//                     checkout scm
//                 }
//             }
            
//             stage('Build Docker Image') {
//                 steps {
//                     script {
//                         sh "docker build -t manar564/python:latest ."
//                     }
//                 }
//             }
            
//             stage('Push Image to Dockerhub') {
//                 steps {
//                     script {
//                         withCredentials([usernamePassword(credentialsId: 'dockerhub',usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD'
//                         )]) {
//                             sh '''
//                                 docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD
//                                 docker push manar564/python:latest
//                                 docker logout
//                             '''
//                         }
//                     }
//                 }
//             }
//         }
//     }
// }

