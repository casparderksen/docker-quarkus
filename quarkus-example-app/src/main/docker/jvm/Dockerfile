FROM quarkus/jvm

LABEL org.label-schema.description="${project.description}" \
      org.label-schema.name="${project.artifactId}" \
      org.label-schema.version=${project.version} \
      org.label-schema.vcs-url=${git.remote.origin.url} \
      org.label-schema.vcs-ref=${git.commit.id}

# Add Quarkus app
COPY target/lib/* ${JAVA_APP_DIR}/lib/
COPY target/*-runner.jar ${JAVA_APP_DIR}/application.jar