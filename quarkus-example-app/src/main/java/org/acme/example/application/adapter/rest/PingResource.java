package org.acme.example.application.adapter.rest;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/ping")
@Tag(name = "Ping service", description = "Service for testing if the application is reachable")
public class PingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Tests if the application is reachable")
    @APIResponse(responseCode = "200", description = "On success")
    public String ping() {
        return "pong";
    }
}
