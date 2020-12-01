# About

This is a microservices chassis for building applications with JEE8/MicroProfile/Docker, based on Quarkus.
Datasource and database-specific migration scripts can be selected by specifying a configuration profile.
Unit-integration tests are ran against an H2 in-memory database. A Docker compose example demonstrates
integration with an Oracle database and Prometheus for monitoring.

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
- Datasource for H2 (dev/test) and Oracle (prod)
- Flyway database migrations (dependent on selected database)
- MicroProfile Config configuration
- MicroProfile Health Extensions for JVM metrics and system health

## Test frameworks

- JUnit5 unit testing
- AssertJ fluent tests
- RestAssured unit tests for JAX-RS endpoints
- ArchUnit architecture tests (Onion Architecture)

# Endpoints

MicroProfile:
- Health: [http://localhost:8080/health](http://localhost:8080/health)
- Metrics: [http://localhost:8080/metrics](http://localhost:8080/metrics)
- OpenAPI: [http://localhost:8080/openapi](http://localhost:8080/openapi)

MicroProfile Extension UIs:
- Swagger UI: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

Resources:
- Ping [http://localhost:8080/api/ping](http://localhost:8080/api/ping)
- Version info[http://localhost:8080/api/info](http://localhost:8080/api/info)
- CRUD resource example: [http://localhost:8080/api/documents](http://localhost:8080/api/documents)

# Building and running the application

Build and test the application with

    $ mvn verify

The Maven build uses a parent POM for dependency management and shared build configuration.
Database drivers for H2 and Oracle are included via Maven profiles (`h2` and `oracle`).
These profiles are activated by default and need to be specified explicitly when specifying other profiles.
    
##  Running from Maven in development mode

Go to the directory [`quarkus-example-app`](quarkus-example-app) for running the application from Maven
in development mode (`dev` profile) with an embedded H2 database:

To run the application from Maven in dev mode with embedded H2 database:

    $ mvn quarkus:dev

## Running from IntelliJ

Create a Run Configuration as follows:
* Select Run > Edit Configurations...
* Select Add New Configuration (`+`) > Maven
* Name: `quarkus-example-app [quarkus:dev]`
* Parameters:
  * Working directory: `quarkus-example-app`
  * Command line: `quarkus:dev`
* General:
  * Maven home directory: Maven 3.6.2 or higher

## Debugging the application

From IntelliJ:
* Start the Run Configuration in debug mode
* Select Run > Attach to Process... 
* Select the Java process listening at port 5005

The `JAVA_TOOL_OPTIONS` environment variable can also be defined in the Docker environment to enable debugging
without altering the container image.

    JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

## Running from the command line

The example application uses an Oracle database as default datasource.
See [here](#docker-compose-example) for running an Oracle database as Docker container.
To run the application from the command line with an Oracle datasource:

    $ export QUARKUS_DATASOURCE_URL=jdbc:oracle:thin:@//localhost:1521/ORCLPDB1
    $ java -jar target/quarkus-example-app-1.0.0-SNAPSHOT-runner.jar

## Using the application
    
Check the application health with `httpie`:

    $ http :8080/health
    
Explore the API:

    $ http :8080/openapi

Add a document:

    $ http POST :8080/api/documents name=foo
    
Retrieve all documents:

    $ http :8080/api/documents  

# Flyway Maven plugin

The application uses Flyway to migrate the database to the required schema version at startup.
It is also possible to test database migrations via the Flyway Maven plugin. 

Apply migrations:

    $ mvn flyway:migrate -Poracle

Clean database:

    $ mvn flyway:clean -Poracle

# Docker

## Building Docker base images

The [`quarkus-docker`](quarkus-docker) module defines Docker base images for running JVM and native applications.
These base images need to be built once, before building the [`quarkus-example-app`](quarkus-example-app) image.
Go to this directory and build the Docker base images with:

    $ export QUARKUS_DATASOURCE_URL=jdbc:oracle:thin:@//$(hostname):1521/ORCLPDB1
    $ mvn package -Pdocker 

## Building and running from Docker

Go to the directory [`quarkus-example-app`](quarkus-example-app). Build the Docker image as follows:

    $ mvn package -Pdocker,oracle

Run the application in Docker from Maven:

    $ mvn docker:run -Pdocker
    
To run the application in Docker from the command-line:

    $ export QUARKUS_DATASOURCE_URL=jdbc:oracle:thin:@//$(hostname):1521/ORCLPDB1
    $ docker run --rm -it -p 8080:8080 -e QUARKUS_DATASOURCE_URL=${QUARKUS_DATASOURCE_URL} quarkus/quarkus-example-app
    
# Docker Compose example

The directory [docker-compose](docker-compose) contains a Docker Compose configuration to run a containerized application 
and Oracle database.

## Prerequisites

First build an Oracle container image as described in [https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance](https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance). 
For Oracle Database 19.3.0 Enterprise Edition this involves the following steps:

1. Place `LINUX.X64_193000_db_home.zip` in `dockerfiles/19.3.0`.
2. Go to `dockerfiles` and run `buildDockerImage.sh -v 19.3.0 -e`

## Build the application

Go to directory [`quarkus-example-app`](quarkus-example-app) and build the application image:
 
     $ mvn package -Pdocker,oracle

## Start Oracle and build the database

Go to the directory [`docker-compose`](docker-compose). First start the database container:

    $ docker-compose up -d oracledb
    $ docker-compose logs -f oracledb

Follow the log file and wait until the database is ready to use. 

## Running the application:

When the database is ready start the application container:

    $ docker-compose up -d  example-app
    $ docker-compose logs -f example-app
    
# Building native executables

## Prerequisites

Install GraalVM. Om Mac with MacPorts installed:

    $ sudo port install openjdk11-graalvm
    $ export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk11-graalvm/Contents/Home
    $ sudo $JAVA_HOME/bin/gu install native-image
    
## Building the native executable

TODO: add supported database

    $ mvn package -Pnative
    
## No native Oracle support

Quarkus does not support native executables with the Oracle JDBC driver.
See [here](https://github.com/quarkusio/quarkus/issues/1658).

# References and attributions

Quarkus:
- [Quarkus guides](https://quarkus.io/guides/)

MicroProfile:
- [MicroProfile Config](https://github.com/eclipse/microprofile-config)
- [MicroProfile Health](https://github.com/eclipse/microprofile-health)
- [MicroProfile JWT](https://github.com/MicroProfileJWT/eclipse-newsletter-sep-2017)
- [MicroProfile Metrics](https://github.com/eclipse/microprofile-metrics/blob/master/spec/src/main/asciidoc/metrics_spec.adoc)
- [MicroProfile Rest Client](https://github.com/eclipse/microprofile-rest-client)
- [MicroProfile OpenAPI](https://github.com/eclipse/microprofile-open-api/blob/master/spec/src/main/asciidoc/microprofile-openapi-spec.adoc)
- [MicroProfile Extensions](https://www.microprofile-ext.org)

Java in Docker:
- [run-java-.sh](https://github.com/fabric8io-images/run-java-sh/blob/master/fish-pepper/run-java-sh/readme.md)

Testing:
- [ArchUnit architecture testing](https://www.archunit.org)

Prometheus:
- [https://github.com/vegasbrianc/prometheus](https://github.com/vegasbrianc/prometheus)
- [https://github.com/iamseth/oracledb_exporter](https://github.com/iamseth/oracledb_exporter)

Oracle:
- [OJDBC compatibility](https://www.oracle.com/technetwork/database/enterprise-edition/jdbc-faq-090281.html#01_01)
