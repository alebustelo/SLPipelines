def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

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
          expression { return (config.enableFirsThing == 1) }
        }
        steps {
          script {
            sh 'echo This is stage 2'
            sh "echo ${config.stringThing}"
          }
        }
      }
      stage("Stage Three") {
        when {
          expression { return (config.enableSecondThing == 1) }
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
