# harbargerdev-jenkins-shared-lib

This repository contains a Jenkins Shared Library that implements a sample GitFlow-based CI/CD pipeline for Java projects using Maven. It is designed for tutorial and demonstration purposes, showing how to structure Jenkins pipelines for typical enterprise workflows.

## Features
- **Pre-flight Check:** Validates the presence of a `buildArgs.json` file in the project root.
- **Compile, Package & Test:** Uses Maven to compile, package, and run unit tests.
- **SBOM Generation:** Mocks the creation of a Software Bill of Materials (SBOM).
- **Quality & Security Scans:** Runs parallel dummy scans to simulate code quality and security checks.
- **Environment Deployments:**
  - **develop branch:** Publishes a SNAPSHOT artifact and deploys to DEV (mocked).
  - **release/* branch:** Publishes a STAGE artifact and deploys to STAGE (mocked). Includes manual approval and change control number prompt before production steps.
  - **main branch:** Publishes a PRODUCTION artifact and deploys to PRODUCTION (mocked).

## Usage
- Reference the shared library in your Jenkinsfile and call `harbargerdevPipeline()`.
- The pipeline stages and deployment logic are controlled by the current branch, following GitFlow conventions.
- All publish and deploy steps are mocked for demonstration; you can replace them with real integrations as needed.

## Structure
- `vars/harbargerdevPipeline.groovy`: Main pipeline logic.
- `lib/`: Contains Groovy dependencies.
- `README.md`: This documentation.

## Customization
You can extend the pipeline to add real artifact publishing, deployment, notifications, or additional validations as required for your organization.

---
This repository is intended for educational and prototyping use. For production use, review and adapt the pipeline logic to your specific requirements.

