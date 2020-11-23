pipeline {
    agent any
    stages {
        stage ('build') {
            steps {
                echo 'Running build automation'
                sh './gradlew build --no-daemon'
                archiveArtifacts artifacts: 'build/libs/api-0.0.1-SNAPSHOT.jar'
            }
        }
        stage ('deploy') {
            when {
                branch 'main'
            }
            steps {
                echo 'Running deploy automation'
                withCredentials([sshUserPrivateKey(credentialsId: "ec2_credential", userNameVariable: 'USERNAME', keyFileVariable: 'KEY_FILE')]) {
                    sshPublisher(
                        verbose: true,
                        failOnError: true,
                        continueOnError: false,
                        publishers: [
                            sshPublisherDesc(
                                configName: 'Production',
                                sshCredentials: [
                                    username: "$USERNAME",
                                    key: "$KEY_FILE"
                                ],
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: 'build/libs/api-0.0.1-SNAPSHOT.jar',
                                        removePrefix: 'build/libs/',
                                        remoteDirectory: '/tmp',
                                        execCommand: 'sudo java -jar /tmp/api-0.0.1-SNAPSHOT.jar'
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }
    }
}