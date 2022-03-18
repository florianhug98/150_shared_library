#!/usr/bin/env groovy

def call(Map config = [:]) {
  def pom = readMavenPom file: "pom.xml"
  def fullImageName = config.imageName + "${pom.version}"
  
  def dockerStopCommand = "docker stop " + config.imageName
  def dockerRemoveImageCommand = "docker rmi $(docker images -q ${env.DOCKER_BASE_URL)/${fullImageName})"
  def dockerPullCommand = "docker pull ${env.DOCKER_BASE_URL}/${fullImageName}"
  
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
      "'" +
      "${dockerStopCommand};" +
      "${dockerRemoveImageCommand};" +
      "${dockerPullCommand}" +
      "'"
  }
}
