#!/usr/bin/env bash
docker rm ms_one_cont
docker rmi ms_one
mvn clean install
docker build -t ms_one .
docker run -it --net="host" -p 8082 --name ms_one_cont  ms_one
