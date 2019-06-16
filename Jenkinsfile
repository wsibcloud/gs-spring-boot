// Requires pipeline-github-lib plugin to load library from github
@Library('github.com/lachie83/jenkins-pipeline@master')
def pipeline = new io.estrado.Pipeline()

podTemplate(label: 'jenkins-pipeline', containers: [
//    containerTemplate(name: 'jnlp', image: 'jenkinsci/jnlp-slave:2.62', args: '${computer.jnlpmac} ${computer.name}', workingDir: '/home/jenkins', resourceRequestCpu: '500m', resourceLimitCpu: '500m', resourceRequestMemory: '1024Mi', resourceLimitMemory: '1024Mi'),
    containerTemplate(name: 'docker', image: 'docker:1.12.6', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'maven', image: 'maven:3.6.1-jdk-8', command: 'cat', ttyEnabled: true)
//    containerTemplate(name: 'helm', image: 'lachlanevenson/k8s-helm:v2.6.1', command: 'cat', ttyEnabled: true),
//    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.8.3', command: 'cat', ttyEnabled: true)
],
volumes:[
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
]){

  node ('jenkins-pipeline') {
    def pwd = pwd()
    def chart_dir = "${pwd}/charts/hellojava"
    def tags = [env.BUILD_TAG, 'latest']
    def docker_registry_url = "wsibprivateregistry.azurecr.io"
    def app_hostname = "hellojava-devops.ddns.net";
    def docker_email = "allan_shao@wsib.on.ca"
    def docker_repo = "hellojava"
    def docker_acct = "kubernetes"
    def jenkins_registry_cred_id = "acr_creds"

    // checkout sources
    checkout scm

    // set additional git envvars for image tagging
    pipeline.gitEnvVars()

    // Execute Maven build and tests
    stage ('Maven Build & Tests') {

      container ('maven') {
	sh '''
          mvn clean
          mvn package
          cd target
          cp ../src/main/resources/web.config web.config
          pwd
          ls -ltra
          cp todo-app-java-on-azure-1.0-SNAPSHOT.jar app.jar 
          apt-get install zip unzip -qy
          zip todo.zip app.jar web.config
	'''
          def resourceGroup = 'csmworkersportal'
            def webAppName = 'springbootapp01'
		azureWebAppPublish azureCredentialsId: env.AZURE_CRED_ID, resourceGroup: env.RES_GROUP, appName: env.WEB_APP, filePath: "**/todo.zip"
  //          azureWebAppPublish azureCredentialsId: 'jenkinsp', publishType: 'file', resourceGroup: resourceGroup, appName: webAppName, filePath: '*.war', sourceDirectory: 'target', targetDirectory: 'webapps'
    
      }
    }
  
 }
}

