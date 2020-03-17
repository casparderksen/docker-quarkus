# About

This is a microservices chassis for building applications with JEE8/MicroProfile/Docker, based on Quarkus.

## Functionality and integrated frameworks

- RedHat UBI8 minimal base image with OpenJDK JRE11
- Docker container built via Fabric8 Docker Maven Plugin
- Fabric8.io `run-java.sh` entrypoint for JVM tuning and running Java apps in Docker
- Maven parent POM (centralized dependencies)
- Git-commit-id-plugin for runtime application identification (in addition to Maven coordinates)
- Lombok (add plugin to your IDE)
- MapStruct for mapping between domain values and DTOs (add plugin to your IDE)
- JAX-RS resources with OpenAPI annotations
- Swagger UI
- Bean Validation of DTOs
- JPA with transactions
- Datasource for H2 (TODO: Oracle, MySQL, Postgresql)
- Flyway database migrations (dependent on selected database)
- MicroProfile Config configuration
- MicroProfile Health Extensions for JVM metrics and system health
- MicroProfile Config configuration
- MicroProfile Health Extensions for JVM metrics and system health

## Test frameworks

- JUnit5 unit testing
- RestAssured unit tests for JAX-RS endpoints
- ArchUnit architecture tests (Onion Architecture)

# Endpoints

MicroProfile:
- Metrics: [http://localhost:8080/metrics](http://localhost:8080/metrics)
- OpenAPI: [http://localhost:8080/openapi](http://localhost:8080/openapi)

MicroProfile Extension UIs:
- Swagger UI: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)
