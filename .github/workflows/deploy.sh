#!/usr/bin/env bash 
ROOT_DIR=$(cd $(dirname $0) && pwd)

mvn -f ${ROOT_DIR}/../../pom.xml -e -Dspring.profiles.active=production  \
  clean \
  spring-javaformat:apply \
  verify \
  deploy \
  spring-boot:build-image
