pipeline {
    agent {
        kubernetes {
            yamlFile 'KubernetesPod.yaml'
        }
    }
    stages {
        stage('Build') {
            steps {
                container('maven') {
                    withCredentials([string(credentialsId: 'snyk.io', variable: 'SNYK_TOKEN')]) {
                        withCredentials([
                            usernamePassword(
                                credentialsId: 'docker.io',
                                passwordVariable: 'CONTAINER_REGISTRY_PASSWORD',
                                usernameVariable: 'CONTAINER_REGISTRY_USERNAME')]) {
                            sh (label: 'mvn deploy spring-boot:build-image',
                                script: 'export OTEL_TRACES_EXPORTER="otlp" && ./mvnw -V -B deploy -Dmaven.deploy.skip')
                        }
                    }
                }
            }
        }
    }
}
