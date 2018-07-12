#!/bin/bash
source set-host-variables.sh
docker-compose -f ./mysql/docker-compose.yml rm -v