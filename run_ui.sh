docker rm -f ui_cont
docker rmi ui
docker build -t ui .
docker run -d --net="host" --name ui_cont ui
