def call() {
    pipeline{
        agent {
            label 'java'
        }

        tools{
            jdk "java-8"
        }

        environment{
            DOCKER_USER = credentials('dockerhub-user')
            DOCKER_PASS = credentials('docker-pass')
        }

        parameters {
            string defaultValue: '${BUILD_NUMBER}', description: 'the version of the docker image', name: 'VERSION'
        }

        stages{
            stage("Build java app"){
                steps{
                    script{
                        javax.build()
                    }
                }
            }
            stage("build java app image"){
                steps{
                    script{
                        dockerx.build("${DOCKER_USER}/java", "${VERSION}")
                    }
                }
            }
            stage("push java app image"){
                steps{
                    script{
                        dockerx.login("${DOCKER_USER}", "${DOCKER_PASS}")
                        dockerx.push("${DOCKER_USER}/java", "${DOCKER_PASS}")
                    }
                }
            }
        }
    }
}