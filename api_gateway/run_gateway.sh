docker rm gateway_cont
docker rmi gateway_img
mvn clean install
docker build -t gateway_img .
docker run -it -p 5484:8080 --name gateway_cont --link mysql-cont:db --link ms_two_cont:ms2 --link ms_one_cont:ms1 gateway_img
