pipeline {
    agent any

    environment {
        PATH = "/opt/homebrew/bin:${env.PATH}"
        DOCKER_CMD = "/opt/homebrew/bin/docker"
        DOCKER_IMAGE = "leevivl/better-jaksec-api"
        DOCKER_TAG = "latest"
        DOCKER_CREDENTIALS_ID = "docker-pat"
    }

    tools {
        maven "Maven3"
        jdk "JDK17"
    }

    stages {

        stage('Build') {
            steps {
                echo "Building project with Maven..."
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo "Running unit tests..."
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                echo "Generating JaCoCo coverage report..."
                sh 'mvn jacoco:report'
            }
        }

        stage('Copy JaCoCo to Server') {
            steps {
                echo "Copying JaCoCo to server ${REMOTE_HOST}"
                sh """
                    chmod 600 ${SSH_KEY_PATH}
                    scp -i ${SSH_KEY_PATH} -o StrictHostKeyChecking=no -r target/site ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}
                """
            }
        }

        stage('Package') {
            steps {
                echo "Packaging application..."
                sh 'mvn package -DskipTests'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')]) {
                    sh "${DOCKER_CMD} login -u $DOCKER_USER --password-stdin <<< $DOCKER_PASS"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building multi-arch Docker image..."
                sh """
                    ${DOCKER_CMD} buildx create --use --name mybuilder || true
                    ${DOCKER_CMD} buildx build \
                        --platform linux/amd64,linux/arm64,linux/arm/v7 \
                        -t ${DOCKER_IMAGE}:${DOCKER_TAG} \
                        --push .
                """
            }
        }
        stage('Push to Docker Hub') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        ${DOCKER_CMD} login -u $DOCKER_USER --password-stdin <<< $DOCKER_PASS
                        ${DOCKER_CMD} push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        ${DOCKER_CMD} logout
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed!"
        }
    }
}