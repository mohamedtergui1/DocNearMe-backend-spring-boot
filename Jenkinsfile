pipeline {
    agent any

    environment {
        // Docker image name and tag
        DOCKER_IMAGE_NAME = 'docnearme-backend'
        DOCKER_IMAGE_TAG = 'latest'
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
                // Run SonarQube analysis with proper network configuration
                script {
                    // First try to determine the correct SonarQube URL
                    sh 'echo "Testing SonarQube connectivity..."'

                    // Try different possible SonarQube URLs
                    sh '''
                        # Check if SonarQube is available at localhost:9000
                        if curl -s -f http://localhost:9000 > /dev/null; then
                            echo "SonarQube available at localhost:9000"
                            SONAR_HOST_URL="http://localhost:9000"
                        # Check if SonarQube is available at sonarqube:9000 (common Docker service name)
                        elif curl -s -f http://sonarqube:9000 > /dev/null; then
                            echo "SonarQube available at sonarqube:9000"
                            SONAR_HOST_URL="http://sonarqube:9000"
                        # Check if SonarQube is available at host.docker.internal:9000 (for Docker Desktop)
                        elif curl -s -f http://host.docker.internal:9000 > /dev/null; then
                            echo "SonarQube available at host.docker.internal:9000"
                            SONAR_HOST_URL="http://host.docker.internal:9000"
                        else
                            echo "SonarQube not found - skipping SonarQube analysis"
                            exit 0
                        fi

                        # Run SonarQube analysis if we found a working URL
                        if [ ! -z "$SONAR_HOST_URL" ]; then
                            mvn sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=sqa_84f46c1ca221ed0ba44a3cfeff33ee2376c61d8b
                        fi
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
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}