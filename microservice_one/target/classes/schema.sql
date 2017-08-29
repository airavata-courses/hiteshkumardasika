create database todo_db;
use todo_db;

create table todo_db.user (
  userId     VARCHAR(30),
  userName   VARCHAR(30),
  email      VARCHAR(30),
  password VARCHAR (30),
  screenName VARCHAR(30),
  CONSTRAINT pk_users PRIMARY KEY (userId)
);

create table todo_db.todoList (
  itemId VARCHAR(10),
  userId VARCHAR(30),
  item   VARCHAR(500),
  CONSTRAINT pk_todoList PRIMARY KEY (itemId)
);