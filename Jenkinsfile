pipeline {
  agent {
    node {
      label 'new_node'
    }
    
  }
  stages {
    stage('') {
      steps {
        git(url: 'https://github.com/airavata-courses/hiteshkumardasika.git', branch: 'Assignment_2')
        sh '''docker run --name mysql-cont --net="host" -p 3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.5
docker run -d --hostname my-rabbit --name some-rabbit --net="host" -p 5672 -p 15672 rabbitmq:3-management

cd microserviceone
bash run_msone.sh
cd ..
cd microservice_two
bash run_mstwo.sh
cd ..
cd microservice_three
bash run_msthree.sh
cd ..
cd api_gateway
bash run_gateway.sh
'''
      }
    }
  }
}