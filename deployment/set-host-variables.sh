#!/bin/bash
export HOST_IP=$(ip a | sed -En 's/127.0.0.1//;s/172.*.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p')
export KAFKA_ADVERTISED_HOST_NAME=$HOST_IP
echo "HOST_IP: ${HOST_IP}"
echo "KAFKA_ADVERTISED_HOST_NAME: ${KAFKA_ADVERTISED_HOST_NAME}"
