#!/usr/bin/env bash
docker rm gateway_cont
docker rmi gateway_img
mvn clean install
docker build -t gateway_img .
docker run -i -p 8085 --net="host" --name gateway_cont  gateway_img
