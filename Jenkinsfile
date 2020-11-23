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
    }
}