FROM quarkus/native

LABEL org.label-schema.description="${project.description}" \
      org.label-schema.name="${project.artifactId}" \
      org.label-schema.version=${project.version} \
      org.label-schema.vcs-url=${git.remote.origin.url} \
      org.label-schema.vcs-ref=${git.commit.id}

# Add Quarkus app
COPY target/*-runner.jar /opt/application

ENTRYPOINT [ "/opt/application" ]
CMD [ "-Dquarkus.http.host=0.0.0.0" ]