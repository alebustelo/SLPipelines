def call(body) {
  pipeline {
    agent any
    stages {
      stage("Stage One"){
        steps {
          script {
            sh 'echo Test'
            sh 'chmox +x test.sh'
            sh './test.sh'
          }
        }
      }
    }
  }
}
