pipeline {
    agent any

    environment {
        // Docker image name and tag
        DOCKER_IMAGE_NAME = 'docnearme-backend'
        DOCKER_IMAGE_TAG = 'latest'
        SONAR_HOST_URL = 'http://sonarqube:9000'
        // Email recipient
        EMAIL_RECIPIENT = 'simotergui4@gmail.com'
    }

    stages {
        stage('Checkout') {
            steps {
                // Explicit git checkout
                git branch: 'main', url: 'https://github.com/mohamedtergui1/DocNearMe-backend-spring-boot'
            }
        }

        stage('Build') {
            steps {
                // Run Maven build
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run tests
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis
                script {
                    sh 'echo "Testing SonarQube connectivity..."'

                    sh '''
                        mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=sqa_84f46c1ca221ed0ba44a3cfeff33ee2376c61d8b
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                // Build Docker image with shell commands
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'

            // Send success email
            emailext (
                subject: "SUCCESS: Pipeline '${currentBuild.fullDisplayName}'",
                body: """
                <p>The build was successful!</p>
                <p>Project: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>Build URL: ${env.BUILD_URL}</p>
                """,
                to: "${EMAIL_RECIPIENT}",
                mimeType: 'text/html'
            )
        }
        failure {
            echo 'Pipeline failed!'

            // Send failure email
            emailext (
                subject: "FAILURE: Pipeline '${currentBuild.fullDisplayName}'",
                body: """
                <p>The build failed!</p>
                <p>Project: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>Build URL: ${env.BUILD_URL}</p>
                <p>Please check the console output for more details.</p>
                """,
                to: "${EMAIL_RECIPIENT}",
                mimeType: 'text/html'
            )
        }
    }
}