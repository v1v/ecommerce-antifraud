#!/bin/bash

JENKINS_URL="http://localhost:8080"
JENKINS_CRUMB=$(curl --silent "${JENKINS_URL}/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)")

## TODO: review the CUrl command to run the build in the CI automatically
curl -X POST -H "${JENKINS_CRUMB}" "${JENKINS_URL}/job/antifraud/job/antifraud/job/main/build"
