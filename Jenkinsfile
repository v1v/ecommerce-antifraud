pipeline {
    agent { label 'linux' }
    options {
        disableConcurrentBuilds()
        quietPeriod(7)
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'cache checkout'
                script {
                    env.VERSION = newVersion()
                }
            }
        }
        stage('Build') {
            agent {
                node {
                    label 'linux'
                    customWorkspace "${VERSION}"
                }
            }
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
    post {
        failure {
            notifyBuild('danger')
        }
        success {
            notifyBuild('good')
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

def notifyBuild(status) {
    def blocks =
    [
        [
            "type": "section",
            "text": [
                "type": "mrkdwn",
                "text": "Build ${currentBuild.result} (version: ${newVersion()})\n\n<${env.OTEL_ELASTIC_URL}|View traces>"
            ],
            "accessory": [
                "type": "image",
                "image_url": "https://raw.githubusercontent.com/open-telemetry/opentelemetry.io/main/static/img/logos/opentelemetry-logo-nav.png",
                "alt_text": "OpenTelemetry"
            ]
        ]
    ]
    //slackSend channel: 'cicd', color: status, message: "ready ${env.OTEL_ELASTIC_URL}"
    slackSend(channel: "#cicd", blocks: blocks)
}
