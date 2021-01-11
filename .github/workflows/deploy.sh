#!/usr/bin/env bash 
PROJECT_ID=${GCLOUD_PROJECT}
TAG_NAME=${1:-$(date +%s)}
IMAGE_TAG="production${GITHUB_SHA:-}"
GCR_IMAGE_NAME=gcr.io/${PROJECT_ID}/${APP_NAME}

ROOT_DIR=$(cd $(dirname $0) && pwd)
APP_NAME=bookmark-api


mvn -f ${ROOT_DIR}/../../pom.xml -e -Dspring.profiles.active=production  \
  clean \
  spring-javaformat:apply \
  verify \
  deploy \
  spring-boot:build-image

image_id=$(docker images -q $APP_NAME)
docker tag "${image_id}" ${GCR_IMAGE_NAME}:latest
docker tag "${image_id}" ${GCR_IMAGE_NAME}:${IMAGE_TAG}
docker push ${GCR_IMAGE_NAME}:latest
docker push ${GCR_IMAGE_NAME}:${IMAGE_TAG}
