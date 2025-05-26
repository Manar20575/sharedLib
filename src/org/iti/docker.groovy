package org.iti

class Docker implements Serializable {
    def script

    Docker(script) {
        this.script = script
    }

    def build(String imageName, String version) {
        script.sh "docker build -t ${imageName}:${version} ."
    }

    def login(String username, String password) {
        script.sh "echo ${password} | docker login -u ${username} --password-stdin"
    }

    def push(String imageName, String version) {
        script.sh "docker push ${imageName}:${version}"
    }
}