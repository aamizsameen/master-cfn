
def call() {

      echo "Sending message to Slack"
      slackSend (color: "good",
                 channel: "jenkins-notifications",
                 message: "*SUCCESS:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}")
}


