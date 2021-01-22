node {

    stage('Initialize')
    {
        def dockerHome = tool 'docker'
        def mavenHome  = tool 'maven-3'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') 
    {
        checkout scm
    }

      stage('Build') 
           {
                  docker {
                    reuseNode true
                    image 'openjdk:11.0-jdk-slim'
                    args  '-v /var/run/docker.sock:/var/run/docker.sock --group-add 992'
                }   
            sh 'uname -a'
            sh 'mvn clean install'  
          }

        stage('Test') 
        {
            //sh 'mvn test'
            sh 'ifconfig' 
        }

        /*stage('Deliver') 
          {
                sh 'bash ./jenkins/deliver.sh'
        }*/
}
