node {

    stage('Initialize')
    {
        def dockerHome = tool 'docker'
        def mavenHome  = tool 'maven-3'
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
         env.WORKSPACE_LOCAL = sh(returnStdout: true, script: 'pwd').trim()
	  env.TESTCONTAINERS_RYUK_ENABLED=true  
    }

    stage('Checkout') 
    {
        checkout scm
    }
/*stage('Cucumber Tests') {
        withMaven(maven: 'maven-3') {
            sh """
			cd ${env.WORKSPACE_LOCAL}
			mvn clean test
		"""
        }
    }*/
withEnv(['TESTCONTAINERS_RYUK_DISABLED=true',
'WORKSPACE_LOCAL=${env.WORKSPACE_LOCAL}'
          ]) {
      stage('Build') 
           {
	//docker.image('alpine:3.5').inside {
           echo "ryuk disabled is ${TESTCONTAINERS_RYUK_DISABLED}"
           echo "WORKSPACE_LOCAL is ${WORKSPACE_LOCAL}"
            sh 'uname -a'
            sh 'mvn clean install'  
   //     }

          }
}
    stage('Expose report') {
        archive '**/cucumber.json'
        cucumber '**/cucumber.json'
    }
    stage('Import tasks to Jira for Features') {


		def xrayConnectorId = "2cc784ab-688a-4c1c-b0e7-e56017cfca7e"
	step([$class: 'XrayImportFeatureBuilder', folderPath: 'target/test-classes/resources/features', projectKey: 'CAL', serverInstance: xrayConnectorId])

	   /* def response = sh(script: "curl -u mnarin:mnarin -X GET -H 'Content-Type: application/json' 'http://10.150.17.73:8100/rest/api/2/issue/10123'", returnStdout: true)
	     def response2 = sh(script: '$XRAY_ISSUES_MODIFIED', returnStdout: true)
		echo response
	    echo "Deneme" +response2*/

    }
    stage('Import results to Xray') {

		def description = "[BUILD_URL|${env.BUILD_URL}]"
		def labels = '["regression","automated_regression"]'
		def environment = "DEV"
		def testExecutionFieldId = 10007
		def testEnvironmentFieldName = "customfield_10131"
		def projectKey = "CAL"
		def xrayConnectorId = "2cc784ab-688a-4c1c-b0e7-e56017cfca7e"
		def info = '''{
				"fields": {
					"project": {
					"key": "''' + projectKey + '''"
				},
				"labels":''' + labels + ''',
				"description":"''' + description + '''",
				"summary": "Automated End to end Test Execution @ ''' + env.BUILD_TIME + ' ' + environment + ''' " ,
				"issuetype": {
				"id": "''' + testExecutionFieldId + '''"
				},
				"''' + testEnvironmentFieldName + '''" : [
				"''' + environment + '''"
				]
				}
				}'''
			echo info
	 step([$class: 'XrayImportBuilder', endpointName: 'CUCUMBER_MULTIPART', importFilePath: 'target/cucumber.json', importInfo: info, inputInfoSwitcher: 'fileContent', serverInstance: xrayConnectorId ])
    }

        /*stage('Deliver') 
          {
                sh 'bash ./jenkins/deliver.sh'
        }*/
}
