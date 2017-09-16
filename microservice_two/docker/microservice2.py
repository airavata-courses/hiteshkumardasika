import ConfigParser
import flask_cors
import random
import pika
import json


from flask_cors import CORS, cross_origin
from flask.ext.sqlalchemy import SQLAlchemy
from flask import Flask, jsonify, request

app = Flask(__name__)
CORS(app)
# Read config file
config = ConfigParser.ConfigParser()
config.read('/app/todo_db.conf')

# RabbitMQ Configuration
connection = pika.BlockingConnection(pika.ConnectionParameters(host='127.0.0.1'))
channel = connection.channel()
channel.queue_declare(queue='rpc_queue')


# MySQL configurations
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://' + config.get('DB', 'user') + \
                                        ':' + config.get('DB', 'password') + '@' + \
                                        config.get('DB', 'host') + ':3306/' + config.get('DB', 'db')

app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

mysql = SQLAlchemy()


# Mapping the models
class TodoItem(mysql.Model):
    __tablename__ = 'todoList'
    itemId = mysql.Column(mysql.String(10), primary_key=True)
    userId = mysql.Column(mysql.String(30), nullable=False)
    item = mysql.Column(mysql.String(500), nullable=False)

    def __repr__(self):
        return '<todoList (%s, %s, %s) >' % (self.userId, self.itemId,self.item)


@app.route('/')
def hello_world():
    return 'Hello World!'


def createProduct(userId, item):
    mysql.init_app(app)
    mysql.app = app
    # fetch userId and item to be inserted

    todoItem = TodoItem(itemId=1, userId=userId, item=item)

    curr_session = mysql.session  # open database session
    try:
        curr_session.add(todoItem)  # add prepared statment to opened session
        curr_session.commit()  # commit changes
    except:
        curr_session.rollback()
        curr_session.flush()  # for resetting non-commited .add()

    itemId_last = todoItem.itemId  # fetch last inserted id
    data = todoItem.query.filter_by(itemId=itemId_last).first()  # fetch our inserted product

    config.read('/app/todo_db.conf')

    result = [data.userId, data.item]  # prepare visual data

    return json.dumps({'userId': data.userId, 'item': data.item})


def getProduct(userId):
    mysql.init_app(app)
    data = TodoItem.query.filter_by(userId=userId)  # fetch all products on the table

    data_all = []

    for todoItem in data:
        data_all.append([todoItem.itemId, todoItem.userId, todoItem.item]) #prepare visual data

    return json.dumps({data_all})


def createProductFromRabbitMQ(userId, item):
    print("Here the item value is "+item)
    todoItem = TodoItem(itemId=1, userId=userId, item=item)
    result = createProduct(userId, item)
    return result

def fetchItems(userId):
    print "Trying to fetch some data"
    return getProduct(userId)

def on_request(ch, method, props, body):
    print("I entered the on_request method"+body)
    bodyStr = str(body)
    data = bodyStr[bodyStr.index('-')+1:]
    purpose = bodyStr[:bodyStr.index('-')]

    if purpose == "insert":
        item = json.loads(data)
        print "the value here is "+ str(item)
        response = createProductFromRabbitMQ(item['userId'], item['item'])
    elif purpose == "fetch":
        print "the value in elif is "+data
        response = fetchItems(data)
    else:
        print "the value in else is "+ purpose + " and "+data
        response = "Do nothing!!"

    ch.basic_publish(exchange='',
                     routing_key=props.reply_to,
                     properties=pika.BasicProperties(correlation_id=props.correlation_id), body=str(response))
    ch.basic_ack(delivery_tag=method.delivery_tag)


channel.basic_qos(prefetch_count=1)
channel.basic_consume(on_request, queue='rpc_queue')

print(" [x] Awaiting RPC requests")
channel.start_consuming()

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
