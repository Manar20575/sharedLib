#!/usr/bin/env groovy
def call(Map config = [:]) {
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