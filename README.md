# About

This is a microservices chassis for building applications with JEE8/MicroProfile/Docker, based on Quarkus.

## Functionality and integrated frameworks

- RedHat UBI8 minimal base image with OpenJDK JRE11
- Maven BOM and parent POM
- Docker container built via Fabric8 Docker Maven Plugin
- Fabric8.io `run-java.sh` entrypoint for JVM tuning and running Java apps in Docker
- Git-commit-id-plugin for runtime application identification (in addition to Maven coordinates)
