#!/usr/bin/env bash 

ROOT_DIR=$(cd $(dirname $0) && pwd)
APP_NAME=bookmarks-api
PROJECT_ID=${GCLOUD_PROJECT}
TAG_NAME=${1:-$(date +%s)}
IMAGE_TAG="production${GITHUB_SHA:-}"
GCR_IMAGE_NAME=gcr.io/${PROJECT_ID}/twi-${APP_NAME}
cd $ROOT_DIR
# mvn -f ${ROOT_DIR}/../../pom.xml -DskipTests=true \
#   -e -Dspring.profiles.active=production spring-boot:build-image
./gradlew  bootBuildImage --imageName=$IMAGE_NAME
image_id=$(docker images -q $APP_NAME)
docker tag "${image_id}" ${GCR_IMAGE_NAME}:latest
docker tag "${image_id}" ${GCR_IMAGE_NAME}:${IMAGE_TAG}
docker push ${GCR_IMAGE_NAME}:latest
docker push ${GCR_IMAGE_NAME}:${IMAGE_TAG}
