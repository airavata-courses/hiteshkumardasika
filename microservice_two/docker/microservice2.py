import ConfigParser
import flask_cors

from flask_cors import CORS, cross_origin
from flask.ext.sqlalchemy import SQLAlchemy
from flask import Flask, jsonify, request

app = Flask(__name__)
CORS(app)
# Read config file
config = ConfigParser.ConfigParser()
config.read('/app/todo_db.conf')

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


@app.route('/item', methods=['POST'])
def createProduct():
    mysql.init_app(app)
    # fetch userId and item to be inserted
    userId = request.get_json()["userId"]
    item = request.get_json()["item"]

    todoItem = TodoItem(itemId=1,userId=userId, item=item)

    curr_session = mysql.session  # open database session
    try:
        curr_session.add(todoItem)  # add prepared statment to opened session
        curr_session.commit()  # commit changes
    except:
        curr_session.rollback()
        curr_session.flush()  # for resetting non-commited .add()

    itemId_last = todoItem.itemId  # fetch last inserted id
    data = todoItem.query.filter_by(itemId=itemId_last).first()  # fetch our inserted product

    config.read('/root/PycharmProjects/microservice2/todo_db.conf')

    result = [data.userId, data.item]  # prepare visual data

    return jsonify(curr_session=result)

@app.route('/item/<string:userId>', methods=['GET'])
def getProduct(userId):
    mysql.init_app(app)
    data = TodoItem.query.filter_by(userId=userId) #fetch all products on the table

    data_all = []

    for todoItem in data:
        data_all.append([todoItem.itemId, todoItem.userId, todoItem.item]) #prepare visual data

    return jsonify(products=data_all)


if __name__ == '__main__':
    app.run()

