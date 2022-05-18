pipeline {
    options { disableConcurrentBuilds() }
    agent {
      label 'linux'
    }
    stages {
        stage('Build') {
            steps {
                withCredentials([string(credentialsId: 'snyk.io', variable: 'SNYK_TOKEN')]) {
                    withCredentials([
                        usernamePassword(
                            credentialsId: 'docker.io',
                            passwordVariable: 'CONTAINER_REGISTRY_PASSWORD',
                            usernameVariable: 'CONTAINER_REGISTRY_USERNAME')]) {

                        sh (
                        label: 'mvn deploy spring-boot:build-image',
                        script: 'export OTEL_TRACES_EXPORTER="otlp" && ./mvnw -V -B deploy')
                        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                build(job: 'antifraud/deploy-antifraud',
                          parameters: [
                            string(name: 'PREVIOUS_VERSION', value: previousVersion()),
                            string(name: 'VERSION', value: newVersion())
                      ])
            }
        }
    }
}

def previousVersion() {
    def props = readProperties(file: 'versions.properties')
    return props.CURRENT
}

def newVersion() {
    def props = readProperties(file: 'versions.properties')
    return props.NEW
}
