package org.acme.util.adapter.rest.exceptionmappers;


import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps uncaught {@link RuntimeException} to internal server error, in order to avoid returning stack traces.
 */
@Slf4j
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {

        log.error("internal server error", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}