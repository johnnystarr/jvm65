pipeline {
    agent any
    options {
        ansiColor('xterm')
    }
    stages {
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
        stage('Publish') {
       		steps {
       			withCredentials([string(credentialsId: 'ARCHIVA_PASSWORD', variable: 'ARCHIVA_PASSWORD')]) {
       				sh "./gradlew publish"
       			}
       		}
        }
        stage('Clean') {
       		steps {
       			deleteDir()
       		}
        }
    }
}