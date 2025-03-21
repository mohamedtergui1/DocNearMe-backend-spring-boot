pipeline {
    agent any

    environment {
        // SonarQube server URL
        SONAR_HOST_URL = 'http://localhost:9000'
        // SonarQube token (stored in Jenkins credentials)
        SONAR_AUTH_TOKEN = credentials('sonar-token')
    }

    tools {
        // Maven installation configured in Jenkins
        maven 'Maven 3.x'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git
                git branch: 'main', url: 'https://github.com/mohamedtergui1/DocNearMe-backend-spring-boot'
            }
        }

        stage('Build and Test') {
            steps {
                // Run Maven build and tests
                sh 'mvn clean test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('SonarQube') { // SonarQube server name configured in Jenkins
                    sh '''
                        mvn sonar:sonar \
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.login=${SONAR_AUTH_TOKEN}
                    '''
                }
            }
        }
    }

    post {
        success {
            // Notify on success
            echo 'SonarQube analysis completed successfully!'
        }
        failure {
            // Notify on failure
            echo 'SonarQube analysis failed!'
        }
    }
}