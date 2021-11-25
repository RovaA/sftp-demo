pipeline {

    agent any

    stages {

        stage('GetCode') {
            steps {
                git 'https://github.com/RovaA/sftp-demo.git'
                echo 'Get code'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
                echo 'Build'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=e435434531c1ac6a2129c8a80711e4f3efd97161'
                echo 'Test with sonar'
            }
        }

    }

}