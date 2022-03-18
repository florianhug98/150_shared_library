#!/usr/bin/env groovy

def call() {
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile $sshuser" +
      "docker ps"
  }
}
