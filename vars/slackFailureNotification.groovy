def call() {
      echo "Sending message to Slack"
      slackSend (color: "danger",
                 channel: "jenkins-notifications",
                 message: "*FAILED:* Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}")
}
