#!/usr/bin/env bash

source "$(cd $(dirname $0) && pwd)/env.sh"

export APP_NAME=editor-api
export CF_APP_NAME=ttd-${APP_NAME}
export CF_DB_SVC_NAME=ttd-db

cf push --no-start -p target/bookmark-api.jar ${CF_APP_NAME}
cf bs $CF_APP_NAME $CF_DB_SVC_NAME
cf set-env ${CF_APP_NAME} JBP_CONFIG_OPEN_JDK_JRE '{ jre: { version: 11.+}}'
cf set-env ${CF_APP_NAME} PINBOARD_TOKEN $PINBOARD_TOKEN
cf set-env ${CF_APP_NAME} JWT_KEY ${JWT_KEY}
cf set-env ${CF_APP_NAME} SPRING_PROFILES_ACTIVE cloud
cf start ${CF_APP_NAME}
