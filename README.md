# airavata-assignment1
The project contains three micro services written in Java,Python and Go respectively.
<h2>Pre-requisite</h2>

Install go

Install Java

Install Docker

<code>docker run --name mysql-cont -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.5</code>

<h1> Micro-Service One: JAVA </h1>
<br/>
<p> Go to microservice_one directory. Execute the run_msone.sh using the command <br/>
 <code> bash run_msone.sh</code>

The Microservice is a spring application. It is located in microservice_one directory.
<br/>
<code> docker run -it -p 5484:8080 --name app_new --link mysql-cont:db ms_one </code>

<h2>Micro-service Two: Python</h2>

This microservice is a flask-mysql application. It is located in microservice_two directory.<br/>
<code>cd microservice_two</code><br/>
<code>docker build -t ms2 . </code><br/>
<code>docker run -it -p 5001:5000 --name app_2 --link mysql-cont:db ms2</code>

<h3>Micro-service Three: Go </h3>

This microservice is in go language. It is located in microservice_three directory

 <code>cd microservice_three</code>
 
 <code>go get github.com/rs/cors</code>

<code>go build</code>

execute the binary generated. <br/>


<h1>Accessing the UI</h1>

Open INDEX.html. It has links to register and login. Register first and then it is redirected to login.

Enter the username and password to login and now it is redirected to todo.html

It has a textbox to enter the todo and store it in the server.

