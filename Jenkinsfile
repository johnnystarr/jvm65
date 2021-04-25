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
        stage('Clean') {
       		steps {
       			deleteDir()
       		}
        }
        stage('Publish') {
       		steps {
       			withCredentials([string(credentialsId: 'ARCHIVA_PASSWORD', variable: 'ARCHIVA_PASSWORD')]) {
       				sh "./gradlew publish"
       			}
       		}
        }
    }
}