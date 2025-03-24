pipeline {
    agent any

    environment {
        // Docker image name and tag
        DOCKER_IMAGE_NAME = 'docnearme-backend'
        DOCKER_IMAGE_TAG = 'latest'
        SONAR_HOST_URL = 'http://sonarqube:9000'
        // Email recipient
        EMAIL_RECIPIENT = 'simotergui4@gmail.com'
        DOCKER_HUB_CREDENTIALS = credentials('eec1d675-b917-45c9-a44b-075e09ade0f9')
        // Full Docker Hub image name (replace 'your-dockerhub-username' with your actual username)
        DOCKER_HUB_IMAGE = "mohamedtergui/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/mohamedtergui1/DocNearMe-backend-spring-boot'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
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
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                    // Tag for Docker Hub
                    sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_HUB_IMAGE}"
                }
            }
        }

        stage('Docker Push') {
    steps {
        script {
            // Secure way to handle credentials
            withCredentials([usernamePassword(
                credentialsId: 'eec1d675-b917-45c9-a44b-075e09ade0f9',
                passwordVariable: 'DOCKER_HUB_PASSWORD',
                usernameVariable: 'DOCKER_HUB_USERNAME'
            )]) {
                sh '''
                    echo "$DOCKER_HUB_PASSWORD" | docker login -u "$DOCKER_HUB_USERNAME" --password-stdin
                    docker push mohamedtergui/docnearme-backend:latest
                    docker logout
                '''
            }
        }
    }
}
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            emailext (
                subject: "SUCCESS: Pipeline '${currentBuild.fullDisplayName}'",
                body: """
                <p>The build was successful!</p>
                <p>Project: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>Build URL: ${env.BUILD_URL}</p>
                <p>Docker Image: ${DOCKER_HUB_IMAGE}</p>
                """,
                to: "${EMAIL_RECIPIENT}",
                mimeType: 'text/html'
            )
        }
        failure {
            echo 'Pipeline failed!'
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