FROM golang:latest

RUN mkdir /app 

RUN go get -v github.com/rs/cors
ADD . /app/ 

WORKDIR /app 

RUN go build -o main . 
CMD ["/app/main"]

