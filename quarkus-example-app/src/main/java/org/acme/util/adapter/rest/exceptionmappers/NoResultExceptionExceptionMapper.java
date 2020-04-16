package org.acme.util.adapter.rest.exceptionmappers;

import org.acme.util.adapter.rest.Headers;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps {@link NoResultException} to not found response.
 */
@Provider
public class NoResultExceptionExceptionMapper implements ExceptionMapper<NoResultException> {

    @Override
    public Response toResponse(NoResultException exception) {
        var reason = exception.getMessage();
        return Response.status(Response.Status.NOT_FOUND).header(Headers.REASON, reason).build();
    }
}