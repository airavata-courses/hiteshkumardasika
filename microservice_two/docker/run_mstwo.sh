#!/usr/bin/env bash
docker rm ms_two_cont
docker rmi ms_2
docker build -t ms_2 .
docker run -i --net="host" -p 5000 --name ms_two_cont ms_2
