# springboot_microservices_example
An example project illustrating a simple microservices implementation

TODO:
- Fix nexus debian package generation
  - none of the generated artifacts appear in the package list on nexus
  - customer.deb might not appear in package list on nexus because of the "classifier" issue per https://github.com/inventage/nexus-apt-plugin
- Move devTest task to separate build stage
- Create uatTest task based on current devTest task (i.e. pull artifact down from Nexus), and put in "Integration Testing" stage, but don't be destructive with VM...
- Modify devTest task to pull artifact down from Jenkins instead of Nexus
- Add performance testing stage illustrating JMeter usage

SETUP:
- Setup Google Cloud Engine
  - Create Cloud Engine account
  - Install gcloud on your desktop (assumes you're running Ubuntu):
    # export CLOUD_SDK_REPO=cloud-sdk-`lsb_release -c -s`
    # echo "deb http://packages.cloud.google.com/apt $CLOUD_SDK_REPO main" | sudo tee /etc/apt/sources.list.d/google-cloud-sdk.list
    # curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
    # sudo apt-get update && sudo apt-get install google-cloud-sdk
    # gcloud auth login
  - Create Centos 6.7 based VM for running Jenkins build server (will need to open port 8080?):
    # gcloud compute instances create "jenkins-vm" --zone "us-central1-a" --machine-type "g1-small" --image ubuntu-14-10 --network default --tags http-server https-server

- Setup Your Continuous Integration VM:
  - Install Java
    # yum install java-1.8.0-openjdk
  - Install Gradle
    # gradle_version=2.3
    # wget -N http://services.gradle.org/distributions/gradle-${gradle_version}-all.zip
    # sudo unzip -foq gradle-${gradle_version}-all.zip -d /opt/gradle
    # sudo ln -sfn gradle-${gradle_version} /opt/gradle/latest
    # sudo printf "export GRADLE_HOME=/opt/gradle/latest\nexport PATH=\$PATH:\$GRADLE_HOME/bin" > /etc/profile.d/gradle.sh
  - Install Vagrant
    # wget https://dl.bintray.com/mitchellh/vagrant/vagrant_1.7.4_x86_64.rpm
    # sudo rpm -i vagrant_1.7.4_x86_64.rpm
    # vagrant plugin install vagrant-google
    # vagrant box add gce https://github.com/mitchellh/vagrant-google/raw/master/google.box
  - Install Jenkins:
    # yum install jenkins
  - Install Jenkins plugins
    - Delivery Pipeline plugin: https://wiki.jenkins-ci.org/display/JENKINS/Delivery+Pipeline+Plugin
    - Copy Artifact plugin: http://wiki.jenkins-ci.org/display/JENKINS/Copy+Artifact+Plugin
    - Parameterised Trigger plugin: http://wiki.jenkins-ci.org/display/JENKINS/Copy+Artifact+Plugin
    - Promoted Builds plugin: http://wiki.jenkins-ci.org/display/JENKINS/Promoted+Builds+Plugin
    - Release plugin: https://wiki.jenkins-ci.org/display/JENKINS/Release+Plugin
  - Create ssh keys for jenkins
    # sudo su - jenkins
    # ssh-keygen -t rsa
  - Add the SSH key to GCE:
    - Go to console.developer.google.com: Compute -> Compute Engine -> Metadata section of the console, SSH Keys tab.
    - Download your Google Cloud Engine service account JSON key
    - https://console.developer.google.com/ -> APIs & Auth -> Credentials -> <Your Service Account> -> Generate New JSON Key
    - Put the JSON file in a secure location - e.g. ~/.ssh/gce_service_account.json - and chmod 600 it...
  - Install Sonatype Nexus
    - Create a "ci" user who has permission to upload artifacts
    - Provide "ci" user credentials to Gradle build script so it can upload artifacts

JENKINS BUILD PIPELINE:
  - Basic build & test (authorisation roles: techmanager, dev, qa, ops, po)
    - If "release" build
      - Bump release number
      - Generate a release tag
    - Compile (stop on fail)
    - Publish static code analysis report (stop on code complexity threshold breach)
    - Run unit tests (stop on fail)
    - Publish test results report
    - Publish test coverage report (stop on coverage threshold breach)
    - If "release" build
      - Generate package (deb/rpm etc) files
      - Upload artifacts to Nexus
  - Deploy to dev env (authorisation roles: techmanager, dev, qa, ops, po) (trigger modes: {auto-if-success(Basic build & test), push-if-success(Basic build & test)})
    - Create VM if not existing (stop on fail)
    - Stand up VM if not already up (stop on fail)
    - Remove existing app packages from VM if currently deployed (stop on fail)
    - Deploy app packages onto VM (stop on fail) --> sourced directly from Jenkins
    - Run smoke test (stop on fail)
  - Dev integration test (authorisation roles: techmanager, dev, qa, ops, po) (trigger modes: push-if-success(Deploy to dev env))
    - Verify VM is up (stop on fail)
    - Verify correct / expected packages are deployed on VM (stop on fail)
    - Run integration test against VM (stop on fail)
    - Publish test results report
    - Publish code coverage report
  - Deploy to function test env (authorisation roles: qa, ops, po) (trigger modes: push-if-success(Dev integration test))
    - Create VM if not existing (stop on fail)
    - Stand up VM if not already up (stop on fail)
    - Remove existing app packages from VM if currently deployed (stop on fail)
    - Deploy app packages onto VM (stop on fail) --> Sourced from Nexus
    - Run smoke test (stop on fail)
  - Function test (authorisation roles: qa, ops, po) (trigger modes: auto-if-success(Deploy to function test env), push-if-success(Deploy to function test env)))
    - Verify VM is up (stop on fail)
    - Verify correct / expected packages are deployed on VM (stop on fail)
    - Run integration test against VM (stop on fail)
    - Publish test results report
    - Publish code coverage report
  - Deploy to performance test env (authorisation roles: techmanager, dev, ops, po) (trigger modes: push-if-success(Function test))
    - Create VM if not existing (stop on fail)
    - Stand up VM if not already up (stop on fail)
    - Remove existing app packages from VM if currently deployed (stop on fail)
    - Deploy app packages onto VM (stop on fail) --> Sourced from Nexus
    - Run smoke test (stop on fail)
  - Performance test (authorisation roles: techmanager, qa, ops, po) (trigger modes: auto-if-success(Deploy to performance test env), push-if-success(Deploy to performance test env)))
    - Verify VM is up (stop on fail)
    - Verify correct / expected packages are deployed on VM (stop on fail)
    - Run performance test against VM (stop on fail - including if NFRs exceeded)
    - Publish performance test report
    - Publish performance profiling report
  - Deploy to production env (authorisation roles: ops) (trigger modes: push-if-success-with-all-approvals(Performance test, {qa, ops, techmanager, po}))
    - Create VM if not existing (stop on fail)
    - Stand up VM if not already up (stop on fail)
    - Remove existing app packages from VM if currently deployed (stop on fail)
    - Deploy app packages onto VM (stop on fail) --> Sourced from Nexus
    - Run smoke test (stop on fail)

GRADLE BUILD COMMANDS

- "Basic Build & Test" stage
  - clean: delete build directory
  - build: compile code plus unit tests, generate package, execute unit tests, assemble artifacts and generate static code analysis report
  // - localSmokeTest; run smoke tests against localhost:8080
  // - localIntegrationTest; run integration tests against localhost:8080
  // - localPerformanceTest; run load tests against localhost:8080
  - jacocoTestReport; generates a code coverage report
  - javadoc: generate javadocs
  - if "release build"
  // - bumpVersion
  // - tagBuild
    - uploadArchives: upload package to Nexus

- "Deploy To Dev" stage
  - devDeploy: stands up the dev VM (if not already up), installs app from Nexus, then launches app (all via Vagrant)
  // - devSmokeTest: basic sanity check to ensure app is working

- "Dev Integration Test" stage
  - devIntegrationTest

- "Dev Performance Test" stage
  - devPerformanceTest

- "Deploy to SQI" stage
  - sqiDeploy: stands up the dev VM (if not already up), installs app from Nexus, then launches app
  // - sqiSmokeTest: basic sanity check to ensure app is working

- "SQI Integration Test" stage
  - sqiIntegrationTest

- "SQI Performance Test" stage
  // - sqiPerformanceTest

ABOUT MICROSERVICES:
- responsible for single domain functionality vs soa handling cross domain functionality
- distributed systems:
  - decompose a monolithic service infrastructure into individually scalable subsystems, organized through vertical slicing of the stack, and interconnected through a common transport.
  - decomposes the components of a monolith into individual units of deployment, which are able to evolve their own scaling requirements irrespective of the other subsystems
- common transport such as rest/http or protobuf
- stateless
- limited to a vertical slice of domain functionality
- individual microservices may implement optimisation strategies such as caching etc
- The "micro" nomenclature expresses the pattern of responsibility across disparate subsystems, not the code base (which may be as complex as needed).
- often deployable microservices come with their own runtime (tomcat or jetty or springboot etc) - i.e. they are not deployed into an existing running container

Example:
- SDF product catalogue REST api could cache products and / or be horizontally scaled across more servers

REFERENCES:
- Jenkins continuous deployment build pipeline:
  - http://www.infoq.com/articles/orch-pipelines-jenkins
- Gradle 2.7:
  - Vagrant plugin: https://github.com/mitchellh/vagrant-google
  - Packaging plugin: https://github.com/nebula-plugins/gradle-ospackage-plugin
- Nexus 2.11.4:
  - APT plugin: https://github.com/inventage/nexus-apt-plugin
- Microservices and springboot:
  - http://www.infoq.com/articles/boot-microservices
  - https://spring.io/guides/gs/spring-boot/)
