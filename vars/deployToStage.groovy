#!/usr/bin/env groovy

def call(Map config = [:]) {
  def pom = readMavenPom file: "pom.xml"
  def imageName = "${pom.artifactId}"
  def fullImageName = "${imageName}:${pom.version}"
  
  def dockerStopCommand = "docker stop ${imageName}"
  def dockerRemoveImageCommand = "docker rmi \$(docker images -q ${env.DOCKER_BASE_URL}/${imageName})"
  def dockerRemoveContainerCommand = "docker rm ${imageName}"
  def dockerPullCommand = "docker pull ${env.DOCKER_BASE_URL}/${fullImageName}"
  def dockerRunCommand = "docker run -d --name ${imageName} ${env.DOCKER_BASE_URL}/${fullImageName}"
  
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
