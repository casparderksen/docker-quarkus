FROM quarkus/base

LABEL org.label-schema.description="UBI8-minimal base image for Quarkus on OpenJDK JRE11" \
      org.label-schema.name=quarkus-openjdk11 \
      org.label-schema.version=${project.version} \
      org.label-schema.vcs-url=${git.remote.origin.url} \
      org.label-schema.vcs-ref=${git.commit.id}

# Install JRE11
RUN microdnf install java-11-openjdk-headless \
    && microdnf clean all \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/lib/security/java.security

# Add run-java.sh script (downloaded by Maven)
COPY maven/run-java.sh /usr/local/bin
RUN chmod 0755 /usr/local/bin/run-java.sh

# Set Java appliction directory
ENV JAVA_APP_DIR /opt

# Set the JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

EXPOSE 8080
USER nobody

ENTRYPOINT [ "/usr/local/bin/run-java.sh" ]