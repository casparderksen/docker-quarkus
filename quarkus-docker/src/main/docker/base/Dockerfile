FROM registry.access.redhat.com/ubi8/ubi-minimal:8.1

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

LABEL org.label-schema.description="UBI8-minimal base image for Quarkus" \
      org.label-schema.name=quarkus-base \
      org.label-schema.version=${project.version} \
      org.label-schema.vcs-url=${git.remote.origin.url} \
      org.label-schema.vcs-ref=${git.commit.id}

# Install JRE11
RUN microdnf update \
    && microdnf install openssl curl ca-certificates \
    && microdnf clean all



