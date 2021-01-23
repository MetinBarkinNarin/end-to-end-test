node {

    stage('Initialize')
    {
        def dockerHome = tool 'docker'
        def mavenHome  = tool 'maven-3'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
         env.WORKSPACE_LOCAL = sh(returnStdout: true, script: 'pwd').trim()
	  env.TESTCONTAINERS_RYUK_ENABLED=false  
    }

    stage('Checkout') 
    {
        checkout scm
    }
stage('Cucumber Tests') {
        withMaven(maven: 'maven-3') {
            sh """
			cd ${env.WORKSPACE_LOCAL}
			mvn clean test
		"""
        }
    }
      stage('Build') 
           {
 
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
