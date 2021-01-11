#!/usr/bin/env bash

source "$(cd $(dirname $0) && pwd)/env.sh"

export APP_NAME=bookmark-api
export TWI_PREFIX=${TWI_PREFIX:-twi}
export CF_APP_NAME=${TWI_PREFIX}-${APP_NAME}
export DB_SVC_NAME=${TWI_PREFIX}-db

cf push --no-start -p target/bookmark-api.jar ${CF_APP_NAME}
cf bs $CF_APP_NAME $DB_SVC_NAME
cf set-env ${CF_APP_NAME} JBP_CONFIG_OPEN_JDK_JRE '{ jre: { version: 11.+}}'
cf set-env ${CF_APP_NAME} PINBOARD_TOKEN $PINBOARD_TOKEN
cf set-env ${CF_APP_NAME} JWT_KEY ${JWT_KEY}
cf set-env ${CF_APP_NAME} DB_SVC_NAME ${DB_SVC_NAME}
cf set-env ${CF_APP_NAME} SPRING_PROFILES_ACTIVE cloud
cf start ${CF_APP_NAME}