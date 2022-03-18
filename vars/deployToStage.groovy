#!/usr/bin/env groovy

def call(Map config = [:]) {
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
      "'" +
      "docker pull nexus.florian-hug.ch/repository/docker-repo/spring-jenkins-demo-arm:0.2" +
      "'"
  }
}
