package org.acme.example.application.adapter.rest;

import lombok.Getter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/info")
@Tag(name = "Info service", description = "Service for getting application info")
public class InfoResource {

    @Inject
    Config config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Gets the application info")
    @APIResponse(responseCode = "200", description = "On success")
    public ApplicationInfo getInfo() {
        return new ApplicationInfo();
    }

    @Getter
    public class ApplicationInfo {
        private final String groupId = config.getValue("info.project.groupId", String.class);
        private final String artifactId = config.getValue("info.project.artifactId", String.class);
        private final String version = config.getValue("info.project.version", String.class);
        private final String buildTime = config.getValue("info.git.buildTime", String.class);
        private final String commitId = config.getValue("info.git.commitId", String.class);
        private final String branch = config.getValue("info.git.branch", String.class);
    }
}
