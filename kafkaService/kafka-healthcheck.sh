#!/bin/bash

# Attempt to list topics
kafka-topics.sh --list --bootstrap-server 127.0.0.1:9092 > /dev/null 2>&1

# Check the exit status of the previous command
if [ $? -ne 0 ]; then
  echo "Kafka is not healthy"
  exit 1
else
  echo "Kafka is healthy"
  exit 0
fi