#!/usr/bin/env groovy

def call(Map config = [:]) {
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
      "'" +
      "docker pull ${env.DOCKER_BASE_URL}/spring-jenkins-demo-arm:0.2" +
      "'"
  }
}
