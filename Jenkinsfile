pipeline {
    agent any

    environment {
        // Docker image name and tag
        DOCKER_IMAGE_NAME = 'docnearme-backend'
        DOCKER_IMAGE_TAG = 'latest'
        SONAR_HOST_URL = 'http://sonarqube:9000'
        // Email recipient
        EMAIL_RECIPIENT = 'simotergui4@gmail.com'
        DOCKER_HUB_CREDENTIALS = credentials('24e0f419-e756-4410-b24e-b74f127c9645')
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
                        mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=sqa_e5356e2382b91f71bd3edc91f29627e60120be5a
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
                    // Cleaner credential handling
                    withCredentials([usernamePassword(
                        credentialsId: '24e0f419-e756-4410-b24e-b74f127c9645',
                        passwordVariable: 'DOCKER_PASSWORD',
                        usernameVariable: 'DOCKER_USERNAME'
                    )]) {
                        sh """
                            docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
                            docker push ${DOCKER_HUB_IMAGE}
                            docker logout
                        """
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