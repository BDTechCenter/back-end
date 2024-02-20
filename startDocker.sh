#!/bin/bash

mvn_command="mvn clean package -DskipTests"
services=("service-discovery" "api-gateway")

for service in "${services[@]}"
do
  cd ./$service
  $mvn_command
  cd ..
done

docker-compose down
docker-compose up --build