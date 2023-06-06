#!/usr/bin/env bash 

APP_NAME=bookmark-api
IMAGE_NAME=gcr.io/${GCLOUD_PROJECT}/twi-${APP_NAME}
cd $GITHUB_WORKSPACE
./gradlew  bootBuildImage --imageName=$IMAGE_NAME
image_id=$(docker images -q $APP_NAME)
docker tag "${image_id}" ${GCR_IMAGE_NAME}:latest
docker push ${GCR_IMAGE_NAME}:latest

