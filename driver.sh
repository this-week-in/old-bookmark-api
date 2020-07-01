#!/usr/bin/env bash

token=$(curl -XPOST -u jlong:$TWI_PW http://localhost:8000/token )            
# curl -H"Authorization: bearer $token " http://localhost:8000/bookmarks/export  -v
curl http://localhost:8000/bookmarks/export  -v

