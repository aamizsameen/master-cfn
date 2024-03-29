properties([
  parameters([
    choice(name: 'DEPLOY_ENV', choices: ['DEV','PROD','STAGING'], description: 'The target environment' ),
	// choice(name: 'BRANCH', choices: ['dev','prod','staging'], description: 'The target Branch' ),
	choice(name: 'REGION', choices: ['ap-south-1','us-east-2','us-west-1'], description: 'The Target Region'),
	choice(name: 'ACTION', choices: ['create-stack','update-stack'], description: 'To create and update the cloudformation stack'),
	choice(name: 'WAIT', choices: ['stack-create-complete','stack-update-complete'], description: 'To wait until the stack created or updated'),
    string(defaultValue: 'scriptcrunch', name: 'JENKINS_BUILDSERVER_ROLEARN', trim: true, description: 'Jenkins build server role arn' ),
    string(defaultValue: 'BASEINFRA', name: 'INFRA_STACK_NAME', trim: true, description: 'Infra Stack Name'),
   ])
])
pipeline {
    agent {
    	node {
	   label 'build-server'
	}
    }
    stages {
	    stage ('Git Checkout') {
            steps {
                script {
		  checkout scm
		}
	    }
        }   
        // stage ('Switch to target branch') {
        //     steps {
        //         echo "Switching to branch: ${BRANCH}"
        //         sh 'git checkout ${BRANCH}'
        //         sh 'git pull'
        //     }
        // }
        stage ('Execute deployment in dev account') {
            // when { 
            //     expression { params.BRANCH == 'dev' }
            // }
            steps {
                withAWS(role:'arn:aws:iam::317185619046:role/Pramod-Test-EC2-Role', roleAccount:'317185619046'){
                    script {
                        echo "Which Environment"
                        echo "${DEPLOY_ENV}"
                        echo "Executing in $DEPLOY_ENV environment"
                        sh "aws cloudformation $ACTION --stack-name ${INFRA_STACK_NAME}-${DEPLOY_ENV} --template-body file://CreateRdsTemplates/CreateRds-ParentStack.yml --parameters file://CreateRdsTemplates/CreateRdsParameters.json --capabilities CAPABILITY_NAMED_IAM --region ${REGION}"
                        sh "aws cloudformation wait ${WAIT} --stack-name ${INFRA_STACK_NAME}-${DEPLOY_ENV} --region ${REGION}"
                    } 
                }
            }
        }
        // stage ('Execute deployment in prod account') {
        //     when { 
        //         expression { params.BRANCH == 'prod' }
        //     }
        //     steps {
        //         withAWS(role:'arn:aws:iam::317185619046:role/ec2-s3-bnm', roleAccount:'317185619046'){
        //             script {
        //                 echo "Which Environment"
        //                 echo "${DEPLOY_ENV}"
        //                 echo "Executing in $DEPLOY_ENV environment"
        //                 sh "aws cloudformation $ACTION --stack-name ${INFRA_STACK_NAME}-${DEPLOY_ENV} --template-body file://${WORKSPACE}/BaseInfra/master.yaml --parameters file://${WORKSPACE}/BaseInfra/prod-config.json --capabilities CAPABILITY_NAMED_IAM --region ${REGION}"
        //                 sh "aws cloudformation wait ${WAIT} --stack-name ${INFRA_STACK_NAME}-${DEPLOY_ENV} --region ${REGION}"
        //             }
        //         }
        //     }
        // }
    }
    post {
    always {
      cleanWs notFailBuild: true /* clean up our workspace */
    }
  }
}
