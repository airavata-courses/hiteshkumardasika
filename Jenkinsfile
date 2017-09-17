pipeline {
  agent {
    node {
      label 'my_node'
    }
    
  }
  stages {
    stage('My_Stages') {
      steps {
        git(url: 'https://github.com/airavata-courses/hiteshkumardasika.git', branch: 'Assignment_2')
        sh '''sudo docker stop some-rabbit || true && sudo docker rm some-rabbit || true
sudo docker stop mysql-cont || true && sudo docker rm mysql-cont || true
sudo docker run --name mysql-cont --net="host" -p 3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.5
sudo docker run -d --hostname my-rabbit --name some-rabbit --net="host" -p 5672 -p 15672 rabbitmq:3-management
sudo apt install maven
cd microserviceone
sudo bash run_msone.sh
cd ..
cd microservice_two
sudo bash run_mstwo.sh
cd ..
cd microservice_three
sudo bash run_msthree.sh
cd ..
cd api_gateway
sudo bash run_gateway.sh
'''
      }
    }
  }
}