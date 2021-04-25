pipeline {
    agent any
    options {
        ansiColor('xterm')
    }
    stages {
        stage('Checkout') {
            steps {
            	checkout scm
            }
        }
        stage('Assemble') {
            steps {
            	sh "./gradlew assemble"
            }
        }
        stage('Build') {
            steps {
            	sh "./gradlew jar"
            }
        }
        stage('Test') {
            steps {
            	sh "./gradlew test"
            }
        }
    }
}