FROM ubuntu:16.04


#RUN apt-get update -y

RUN apt-get install -y python-pip python-dev

RUN apt-get install -y python-mysqldb

# We copy just the requirements.txt first to leverage Docker cache
COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY . /app

EXPOSE 5000
#ENTRYPOINT [ "python" ]

CMD [ "python","/app/microservice2.py" ]

