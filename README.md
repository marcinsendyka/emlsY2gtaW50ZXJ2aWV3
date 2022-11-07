
### About

Simple Spring-boot demo application, which is showcase of my approach to Java. 

Imaginary problem:

Company wants to have simple sales management, with following use-cases:

1. Store and retrieve items (**implemented**).
2. Record transactions (not implemented)
3. ...

### API

Single `/items` resource with GET `findById` and POST new resource. See tests for details.

### Development

Requires JDK 19.

Build: `./mvnw clean install`

Run local (supports hot reload): `./mvnw spring-boot:run`


### Future improvements / backlog

1. Remove H2 and introduce typical sql database.
   - Remove H2 from test code and use _testcontainers_ to spin up test db.
2. Improve error handling
  - Do not return spring generic errors (stacktraces).
  - Setup consistent approach for returning non 200 HTTP codes.
3. Implement calling 3rd party service (API, Cloud service etc.)
4. Cleanup `pom.xml` from unused libraries.
5. Add more logic, resources (see _About_).
  - Add publishing domain events / async communication.
6. Introduce _OpenApi_.
7. Review use of Spring.
8. Add contract tests.