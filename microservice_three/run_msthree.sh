docker rm -f ms_three_cont
docker rmi ms_3
docker build -t ms_3 .
docker run -d  --net="host" -p 8086 --name ms_three_cont ms_3
