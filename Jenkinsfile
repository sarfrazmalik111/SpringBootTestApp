pipeline {
    agent any
    environment {
        VERSION_NAME="1.0"
    }
    tools {
        jdk 'JDK17'
        maven "Maven"
    }
    stages {
        stage('build') {
            steps {
                echo 'Build step STARTED'
                sh 'echo "build version: ${VERSION_NAME}"'
                sh "mvn clean install -DskipTests"
            }
        }
        stage('test') {
            steps {
                echo 'Test step STARTED'
                //sh 'mvn clean test package'
            }
        }
        stage('deploy') {
            steps {
                echo 'Deploy step STARTED'
                sh 'cp target/ROOT.war /Applications/apache-tomcat-9.0.8/webapps/'
            }
        }
    }
    post {
        always {
            echo 'Build, Test & deploy completed'
        }
        success {
            echo 'Build, Test & deploy completed Successfully'
        }
        failure {
            echo 'Build, Test & deploy completed with Failure'
        }
    }
}