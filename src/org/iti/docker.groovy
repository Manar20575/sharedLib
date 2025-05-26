package org.iti

class Docker implements Serializable {
    def script

    Docker(script) {
        this.script = script
    }

    def login(USERNAME, PASSWORD){
        script.sh "docker login -u ${USERNAME} -p ${PASSWORD}"
    }

    def build(IMAGE_NAME, IMAGE_TAG){
        script.sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
    }

    def push(IMAGE_NAME, IMAGE_TAG){
        script.sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
    }
}


// package org.iti;

// def login(USERNAME, PASSWORD){
//     sh "docker login -u ${USERNAME} -p ${PASSWORD}"
// }

// def build(IMAGE_NAME, IMAGE_TAG){
//     sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
// }

// def push(IMAGE_NAME, IMAGE_TAG){
//     sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
// }