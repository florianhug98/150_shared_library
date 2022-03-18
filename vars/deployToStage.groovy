#!/usr/bin/env groovy

def call(Map config = [:]) {
  def pom = readMavenPom file: "pom.xml"
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
      "'" +
      "docker pull ${env.DOCKER_BASE_URL}/" + config.imageName + ":${pom.version}" +
      "'"
  }
}
