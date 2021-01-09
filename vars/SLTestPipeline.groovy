def call(body) {
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  pipeline {
    agent any
    stages {
      stage("Stage Zero"){
        steps {
          script {
            def enableFirstThing
            def enableSecondThing
            def stringThing
            if (!(config.enableFirstThing == true || config.enableFirstThing == false)) {
              enableFirstThing = true //default value
            } else {
              enableFirstThing = config.enableFirstThing
            }
            if (!(config.enableSecondThing == true || config.enableSecondThing == false)) {
              enableSecondThing = true //default value
            } else {
              enableSecondThing = config.enableSecondThing
            }
            if (config.stringThing == null || config.stringThing == "") {
              stringThing = "default string text" //default value
            } else {
              stringThing = config.stringThing
            }
          }
        }
      }
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
          expression { return (config.enableFirstThing) }
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
          expression { return (config.enableSecondThing) }
        }
        steps {
          script {
            sh 'echo This is stage 3'
          }
        }
      }
    }
  }
}
