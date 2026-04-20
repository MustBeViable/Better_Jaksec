pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "leevivl/better-jaksec-api"
        DOCKER_TAG = "latest"
        DOCKER_CREDENTIALS_ID = "docker-pat"
    }

    tools {
        maven "Maven3"
        jdk "JDK17"
        nodejs "Node20"
        dockerTool "Docker"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
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
                sh 'mvn jacoco:report'
            }
        }

        stage('Copy JaCoCo to Server') {
            steps {
                sh """
                    chmod 600 ${SSH_KEY_PATH}
                    scp -i ${SSH_KEY_PATH} -o StrictHostKeyChecking=no -r target/site ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}
                """
            }
        }

        stage('Checkout Site Repo and Build') {
            steps {
                dir('site') {
                    git branch: 'main',
                        credentialsId: 'git',
                        url: 'https://github.com/MustBeViable/BetterJaksec_frontend.git'
                }

                dir('site/BetterJaksec') {
                    sh '''
                        npm ci
                        cp -f .env.sample .env
                        npm run build
                    '''
                }

                sh '''
                    mkdir -p src/main/resources/static
                    rm -rf src/main/resources/static/*
                    cp -r site/BetterJaksec/dist/* src/main/resources/static/
                '''
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    def dockerHome = tool 'Docker'
                    env.PATH = "${dockerHome}/bin:${env.PATH}"
                }
                withCredentials([usernamePassword(
                    credentialsId: "${DOCKER_CREDENTIALS_ID}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        # Avoid broken credential helpers
                        mkdir -p $WORKSPACE/.docker
                        echo '{}' > $WORKSPACE/.docker/config.json
                        export DOCKER_CONFIG=$WORKSPACE/.docker

                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    '''
                }
            }
        }

        stage('Build & Push Multi-Arch Docker Image') {
            steps {
                sh '''
                    export DOCKER_CONFIG=$WORKSPACE/.docker

                    docker buildx create --use || true

                    docker buildx build \
                        --platform linux/amd64,linux/arm64 \
                        -t ${DOCKER_IMAGE}:${DOCKER_TAG} \
                        --push \
                        .
                '''
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