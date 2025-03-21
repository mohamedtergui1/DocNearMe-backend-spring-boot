pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = 'sqa_e03c6ff8dfed6dcacd9169cd409a7ef9363640b0'
    }

    stages {
        stage('Check Tools') {
            steps {
                script {
                    // Check if Maven is installed
                    def mvnExists = sh(script: 'command -v mvn', returnStatus: true) == 0
                    if (!mvnExists) {
                        error 'Maven (mvn) is not installed or not in the PATH!'
                    }

                    // Check if Docker is installed
                    def dockerExists = sh(script: 'command -v docker', returnStatus: true) == 0
                    if (!dockerExists) {
                        error 'Docker is not installed or not in the PATH!'
                    }

                    // Check if Git is installed
                    def gitExists = sh(script: 'command -v git', returnStatus: true) == 0
                    if (!gitExists) {
                        error 'Git is not installed or not in the PATH!'
                    }

                    echo 'All required tools (Maven, Docker, Git) are installed.'
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/mohamedtergui1/DocNearMe-backend-spring-boot'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed! Check the logs for details.'
        }
    }
}