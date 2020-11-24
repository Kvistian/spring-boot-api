pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                echo 'Running build automation'
                sh './gradlew build --no-daemon'
                archiveArtifacts artifacts: 'build/libs/api-0.0.1-SNAPSHOT.jar'
            }
        }
        stage ('Build docker image') {
            when {
                branch 'main'
            }
            steps {
                echo 'Building docker image..'
                script {
                    app = docker.build("kvistian/api")
                    echo 'Image built'
                    app.inside {
                        sh 'echo $(curl localhost:8080)'
                    }
                }
            }
        }
        stage ('Release docker image') {
            when {
                branch 'main'
            }
            steps {
                echo 'Push docker image to docker hub'
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }
        stage ('Deploy') {
            when {
                branch 'main'
            }
            steps {
                input 'Do you want to deploy ${env.BUILD_NUMBER}?'
                milestone(1)
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2_credential', usernameVariable: 'USERNAME', keyFileVariable: 'KEY_FILE')]) {
                    script {
                        sh "ssh -o StrictHostKeyChecking=no $USERNAME@$prod_ip -i $KEY_FILE \"docker pull kvistian/api:${env.BUILD_NUMBER}\""
                        try {
                            sh "ssh -o StrictHostKeyChecking=no $USERNAME@$prod_ip -i $KEY_FILE \"docker stop api\""
                            sh "ssh -o StrictHostKeyChecking=no $USERNAME@$prod_ip -i $KEY_FILE \"docker rm api\""
                        } catch (err) {
                            echo 'Caught error: $err'
                        }
                        sh "ssh -o StrictHostKeyChecking=no $USERNAME@$prod_ip -i $KEY_FILE \"docker run --restart always --name api -p 8080:8080 -d kvistian/api:${env.BUILD_NUMBER}\""
                    }
                }
            }
        }
    }
}