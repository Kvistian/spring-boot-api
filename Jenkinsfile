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
                input 'Do you want to deploy?'
                milestone(1)
                echo 'Running deploy automation'
                sshPublisher(
                    failOnError: true,
                    continueOnError: false,
                    publishers: [
                        sshPublisherDesc(
                            verbose: true,
                            configName: 'production',
                            transfers: [
                                sshTransfer(
                                    sourceFiles: 'build/libs/api-0.0.1-SNAPSHOT.jar',
                                    removePrefix: 'build/libs/',
                                    remoteDirectory: '/tmp',
                                    execCommand: 'sudo systemctl restart api'
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
}