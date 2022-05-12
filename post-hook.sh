#!/bin/bash

JENKINS_URL="http://localhost:8080"

if [ ! -f jenkins-cli.jar ] ; then
	wget http://localhost:8080/jnlpJars/jenkins-cli.jar
fi
java -jar jenkins-cli.jar -s "${JENKINS_URL}/" -webSocket build antifraud/antifraud/main
