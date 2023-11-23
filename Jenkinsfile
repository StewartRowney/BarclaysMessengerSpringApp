pipeline {
        agent any
        options {
            skipStagesAfterUnstable()
        }
        stages {
            stage('Build') {
                steps {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
//                        sh "${tool('sonar-scanner')}/bin/sonar-scanner -Dsonar.projectKey=myProjectKey -Dsonar.projectName=myProjectName"
                        sh 'mvn clean package sonar:sonar'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deployment successful!!!'
                //sh './jenkins/scripts/deploy.sh'
            }
        }
    }
}
