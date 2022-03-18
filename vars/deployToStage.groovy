#!/usr/bin/env groovy

def call(Map config = [:]) {
  def pom = readMavenPom file: "pom.xml"
  def fullImageName = config.imageName + ":${pom.version}"
  
  def dockerStopCommand = "docker stop " + config.imageName
  def dockerRemoveImageCommand = "docker rmi \$(docker images -q ${env.DOCKER_BASE_URL}/" + config.imageName + ")"
  def dockerRemoveContainerCommand = "docker rm " + config.imageName
  def dockerPullCommand = "docker pull ${env.DOCKER_BASE_URL}/${fullImageName}"
  def dockerRunCommand = "docker run -d --name " + config.imageName + " ${env.DOCKER_BASE_URL}/${fullImageName}"
  
  withCredentials([sshUserPrivateKey(credentialsId: 'app-ssh', keyFileVariable: 'keyfile', usernameVariable: 'sshuser')]) {
    sh "ssh -i $keyfile -o StrictHostKeyChecking=no $sshuser " +
      "'" +
      "${dockerStopCommand};" +
      "${dockerRemoveContainerCommand};" +
      "${dockerRemoveImageCommand};" +
      "${dockerPullCommand};" +
      "${dockerRunCommand}" +
      "'"
  }
}
