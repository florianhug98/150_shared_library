#!/usr/bin/env groovy

def call() {
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', passwordVariable: 'password', usernameVariable: 'username')]) {
      sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
        "docker login -p $password -u $username ${env.DOCKER_REGISTRY_BASE_URL} " +
        "docker ps"
    }
  }
}
