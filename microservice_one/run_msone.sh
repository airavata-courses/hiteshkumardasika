docker rm ms_one_cont
docker rmi ms_one
cd microservice_one
mvn clean install
docker build -t ms_one .
docker run -it -p 5484:8080 --name ms_one_cont --link mysql-cont:db --link some-rabbit:my-rabbit ms_one