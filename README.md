# springboot_microservices_example
An example project illustrating a simple microservices implementation

DEPENDENCIES / REFERENCES:
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

BUILD PIPELINE:
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

BUILD INSTRUCTIONS

Normal Build
- Launch Basic build & test job

Release Build
- Bump version number in gradle.properties
- Launch Basic build & test job

TODO:
- Fix nexus package generation
  - none of the generated artifacts appear in the package list on nexus
  - customer.deb might not appear in package list on nexus because of the "classifier" issue per https://github.com/inventage/nexus-apt-plugin
- Integration testing:
  - gradle command line supports target hostname argument for integration testing
- Performance testing:
  - gradle command line supports target hostname argument for performance testing

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

- 
