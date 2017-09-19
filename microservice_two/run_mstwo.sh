#!/usr/bin/env bash
docker rm -f ms_two_cont
docker rmi ms_2
docker build -t ms_2 .
docker run -d --net="host" -p 5000 --name ms_two_cont ms_2
