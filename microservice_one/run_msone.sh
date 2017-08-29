cd microservice_one
mvn clean install
docker build -t ms_one .
docker run -it -p 5484:8080 --name app_new --link mysql-cont:db msone
