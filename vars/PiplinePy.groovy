#!/usr/bin/env groovy

def piplinePy(Map config = [:]) {
    def dockerT1 = new org.iti.Docker(this)
    
    node('java') {
        stage('Checkout') {
            checkout scm
        }
        
        withCredentials([
            usernamePassword(
                credentialsId: 'dockerhub-user', 
                usernameVariable: 'DOCKER_USER', 
                passwordVariable: 'DOCKER_PASS'
            )
        ]) {
            stage("Build Docker Image") {
                dockerT1.build("${DOCKER_USER}/python", "${BUILD_NUMBER}")
            }
            
            stage("Push Docker Image") {
                dockerT1.login("${DOCKER_USER}", "${DOCKER_PASS}")
                dockerT1.push("${DOCKER_USER}/python", "${BUILD_NUMBER}")
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
