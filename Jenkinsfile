pipeline {
    agent any

    stages {
        stage('Docker-Compose') {
            steps {
                echo 'run Docker-Compose'
                sh 'docker-compose version'
                sh 'docker-compose down'
                sh 'docker-compose up --build'

            }
        }
    }
}
