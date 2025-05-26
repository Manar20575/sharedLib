package org.iti;

// def build(){
//     sh "mvn clean package install"
// }
// def test(){
//     sh "mvn test"
// }
class Java implements Serializable {
    def script

    Java(script) {
        this.script = script
    }


    def build(IMAGE_NAME, IMAGE_TAG){
        script.sh "mvn clean package install"
    }

    def test(IMAGE_NAME, IMAGE_TAG){
        script.sh "mvn test"
    }
}
