import com.niceincontact.pipeline.*

@Library('cicd-jenkins-shared-libraries@v7.0.3') _

pipeline {
     agent any

        options {
            ansiColor('xterm')
            buildDiscarder(logRotator(numToKeepStr: '5'))
        }
    stages {
        stage("Build Docker Images") {
            steps {
                script {
                   checkout scm
                   echo "Build number is ${currentBuild.number}"
                   loadTestsImage = docker.build("qa-load-tests", "-f Dockerfile ${WORKSPACE}")
                }
            }
        }
        stage("Push to ic-dev") {
            when {
                anyOf {
                        branch 'master'
                        branch 'development'
                      }
             }
            steps{
                script{
                    publishDocker(loadTestsImage, "qa-load-tests", "300813158921.dkr.ecr.us-west-2.amazonaws.com","300813158921")
                }
            }
        }
         stage("Push to ic-test") {
            when {
                anyOf {
                        branch 'master'
                      }
             }
            steps{
             timeout(time: 5, unit: "MINUTES") {
                    input "Do you approve the Test image push? "
                }
                script{
                    publishDocker(loadTestsImage, "qa-load-tests", "265671366761.dkr.ecr.us-west-2.amazonaws.com","265671366761")
                }
            }
        }
         stage("Push to ic-stage") {
            when {
                anyOf {
                        branch 'master'
                      }
             }
            steps{
            timeout(time: 5, unit: "MINUTES") {
                    input "Do you approve the Staging image push? "
                }
                script{
                    publishDocker(loadTestsImage, "qa-load-tests", "545209810301.dkr.ecr.us-west-2.amazonaws.com","545209810301")
                }
            }
        }
        stage("Push to ic-prod") {
            when {
                anyOf {
                        branch 'master'
                      }
             }
            steps{
            timeout(time: 5, unit: "MINUTES") {
                    input "Do you approve the Prod image push? "
                }
                script{
                    publishDocker(loadTestsImage, "qa-load-tests", "737494165703.dkr.ecr.us-west-2.amazonaws.com","737494165703")
                }
            }
        }
        stage("Notify Helm Chart") {
            when {
                anyOf { branch 'development'
                        branch 'master'
                      }
            }
            steps {
                script{
                    def commit = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
                    def tag = commit.trim().take(8)
                    withCredentials([usernamePassword(credentialsId: "kubernetes-workloads-promotion", passwordVariable: 'GIT_TOKEN', usernameVariable: 'GIT_USER')]) {
                        sh """
                        curl -LJ https://github.com/cli/cli/releases/download/v2.0.0/gh_2.0.0_linux_amd64.tar.gz > gh_2.0.0_linux_amd64.tar.gz
                        tar -zxvf gh_2.0.0_linux_amd64.tar.gz
                        echo $GIT_TOKEN | gh_2.0.0_linux_amd64/bin/gh auth login --with-token
                        gh_2.0.0_linux_amd64/bin/gh workflow run promotion.yaml --repo inContact/acddevops-kubernetes-charts --ref main -f key=qa-load-tests -f changes='[{"jsonPath":"image.tag","value":"${tag}"}]'
                        """
                    }
                }
            }
        }
    }
    post {
        success {
              echo "========Build Succeeded========"
        }

        failure {
            echo "========Build Failed========"
        }

        cleanup {
            echo "========Cleaning up the mess we made========"
            cleanWs()
        }
    }
}
def publishDocker(builtImage, String repoName, String registry, String accountId) {
    CheckCreateRepo(registry, repoName, accountId)
    def commit = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
    def tag = commit.trim().take(8)
    docker.withRegistry("http://${registry}", "ecr:us-west-2:ServiceAccess-jenkins-ecr_${accountId}") {
        if(!dockerImageExists(registry, tag, repoName)){
            builtImage.push("${tag}")
        }
    }
}

def CheckCreateRepo(String registry, String repoName, String accountId) {

    echo "Checking if repo '${repoName}' exists in ${registry} registry (us-west-2)"

    withAWS(role: "ServiceAccess-jenkins-ecr", roleAccount: "${accountId}", roleSessionName: 'jenkins-ecr-session') {
        if(!sh (script: "aws ecr describe-repositories --repository-names ${repoName} --region us-west-2", returnStatus: true)) {
            echo "Repo exists"
        }
        else
        {
            echo "Repo does not exist, creating"
            sh("aws ecr create-repository --repository-name ${repoName} --image-tag-mutability IMMUTABLE --image-scanning-configuration scanOnPush=true  --region us-west-2")
        }
    }
}

boolean dockerImageExists(String registry, String tag, String repoName) {
    def exists = true
    def imageUri = "${registry}/${repoName}:${tag}"
    try {
        sh("docker pull ${imageUri}")
        echo "'${imageUri}' docker image exists"
    } catch (e) {
        echo "'${imageUri}' docker image does not exist"
        exists = false
    }
    return exists
}