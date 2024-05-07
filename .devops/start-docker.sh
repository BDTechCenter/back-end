#!/bin/bash

docker compose -f .devops/docker-compose.yaml down
docker compose -f .devops/docker-compose.yaml build
docker compose -f .devops/docker-compose.yaml up