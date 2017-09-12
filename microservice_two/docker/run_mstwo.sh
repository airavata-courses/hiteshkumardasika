docker rm ms_two_cont
docker rmi ms_2
docker build -t ms_2 .
docker run -it -p 5015:5000 --name ms_two_cont --link mysql-cont:db --link some-rabbit:test-rabbit ms_2
