def call(body) {
  pipeline {
    agent any
    stages {
      stage("Stage One"){
        steps {
          script {
            sh 'echo Test'
            sh 'chmod +x test.sh'
            sh './test.sh'
          }
        }
      }
      stage("Stage Two") {
        when {
          body.enableFirsThing == 1
        }
        steps {
          script {
            sh 'echo This is stage 2'
            sh "echo ${body.stringThing}"
          }
        }
      }
      stage("Stage Three") {
        when {
          body.enableSecondThing == 1
        }
        steps {
          script {
            sh 'This is stage 3'
          }
        }
      }
    }
  }
}
