docker rm ms_one_cont
docker rmi ms_one
mvn clean install
docker build -t ms_one .
docker run -it -p 5485:8082 --name ms_one_cont --link mysql-cont:db ms_one
