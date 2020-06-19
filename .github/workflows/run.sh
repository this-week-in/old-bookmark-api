#!/usr/bin/env bash 

mvn -e -Dspring.profiles.active=production verify deploy
./deploy/cf.sh
./deploy/deploy.sh

