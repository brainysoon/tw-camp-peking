pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh './gradlew build'
                archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh './test.sh'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying..'
                sh './deploy.sh'
            }
        }
    }
}