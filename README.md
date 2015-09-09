# springboot_microservices_example
An example project illustrating a simple microservices implementation

Todo:
- Integration testing:
  - gradle command line supports target hostname argument for integration testing
  - launches vm based on GCE immutable images to speed environment standup
  - runs integration test against instantiated vm 
- Performance testing:
  - gradle command line supports target hostname argument for performance testing
  - launches vm based on GCE immutable images to speed environment standup
  - runs performance test against instantiated vm 
- Telstra workflow:
  - basic build and test
  - static code analysis - if basic build and test succeeds
  - deploy to dev - if static code analysis succeeds
  - run acceptance tests in dev - if deploy to dev succeeds
  - run regression tests in dev - if acceptance tests succeed
  - run performance tests in dev - if regression tests succeed
  - deploy to SQI - if performance tests in dev succeed
  - run regression test in SQI - if deploy to SQI succeeds
  - deploy to UAT - if system tester approves regression tests in SQI
  - run regression test in UAT - if deploy to UAT succeeds
  - deploy to SVT - if QA and product owner approve integration test 
  - run performance test in SVT - if deploy to SVT succeeds
  - deploy to pre-prod - if performance engineer and technology manager approve performance tests
  - deploy to production - if ops and product owner and technology manager approves
- Use of Nexus
  - TBD

microservices:
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
