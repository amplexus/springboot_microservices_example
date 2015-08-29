# springboot_microservices_example
An example project illustrating a simple microservices implementation

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
