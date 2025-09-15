// Jenkins Shared Library: GitFlow Pipeline
// Supports develop (DEV), release (STAGE), and main (PROD) workflows
def call(Map params = [:]) {
    pipeline {
        agent any
        stages {
            stage('Pre-flight check') {
                steps {
                    script {
                        if (!fileExists('buildArgs.json')) {
                            error 'Pre-flight check failed: buildArgs.json not found in workspace root.'
                        } else {
                            echo 'Pre-flight check passed: buildArgs.json found.'
                        }
                    }
                }
            }
            stage('Compile') {
                steps {
                    sh 'mvn compile'
                }
            }
            stage('Package & Test') {
                steps {
                    sh 'mvn package'
                    sh 'mvn test'
                }
            }
            stage('Generate SBOM') {
                steps {
                    script {
                        echo 'Generating SBOM (mock)...'
                        writeFile file: 'sbom.json', text: '{ "sbom": "mock" }'
                    }
                }
            }
            stage('Quality & Security Scans') {
                parallel {
                    stage('Dummy Scan (UTC/Code Quality)') {
                        steps {
                            script {
                                echo 'Running dummy code quality scan (mock)...'
                                writeFile file: 'code_quality_report.txt', text: 'Code Quality: PASS (mock)'
                            }
                        }
                    }
                    stage('Security Scan') {
                        steps {
                            script {
                                echo 'Running dummy security scan (mock)...'
                                writeFile file: 'security_report.txt', text: 'Security Scan: PASS (mock)'
                            }
                        }
                    }
                }
            }
            stage('Publish & Deploy to DEV') {
                when {
                    branch 'develop'
                }
                steps {
                    script {
                        echo 'Publishing SNAPSHOT artifact (mock)...'
                        writeFile file: 'published_artifact.txt', text: 'Artifact published: SNAPSHOT (mock)'
                        echo 'Deploying to DEV environment (mock)...'
                        writeFile file: 'dev_deploy.txt', text: 'Deployed to DEV (mock)'
                    }
                }
            }
            stage('Publish & Deploy to STAGE') {
                when {
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
                steps {
                    script {
                        echo 'Publishing STAGE artifact (mock)...'
                        writeFile file: 'published_stage_artifact.txt', text: 'Artifact published: STAGE (mock)'
                        echo 'Deploying to STAGE environment (mock)...'
                        writeFile file: 'stage_deploy.txt', text: 'Deployed to STAGE (mock)'
                    }
                }
            }
            stage('Ready for Production?') {
                when {
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
                steps {
                    input {
                        message 'Ready to publish to production?'
                        ok 'Continue'
                    }
                }
            }
            stage('Change Control Number') {
                when {
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
                steps {
                    script {
                        def changeControl = input(
                            id: 'changeControl',
                            message: 'Enter Change Control Number for Production:',
                            parameters: [string(defaultValue: '', description: 'Change Control Number', name: 'CC_NUMBER')]
                        )
                        if (!changeControl) {
                            error 'Change Control Number is required for production publish.'
                        }
                        echo "Change Control Number provided: ${changeControl}"
                        writeFile file: 'change_control.txt', text: "Change Control Number: ${changeControl}"
                    }
                }
            }
            stage('Publish Artifact to Production') {
                when {
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
                steps {
                    script {
                        echo 'Publishing PRODUCTION artifact (mock)...'
                        writeFile file: 'published_prod_artifact.txt', text: 'Artifact published: PRODUCTION (mock)'
                    }
                }
            }
            stage('Deploy to Production') {
                when {
                    branch pattern: 'release/.*', comparator: 'REGEXP'
                }
                steps {
                    script {
                        echo 'Deploying to PRODUCTION environment (mock)...'
                        writeFile file: 'prod_deploy.txt', text: 'Deployed to PRODUCTION (mock)'
                    }
                }
            }
        }
    }
}
