package org.acme.util.adapter.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public interface Responses {

    static <T> Response getEntityResponse(T entity, UriInfo uriInfo) {
        final var self = Links.getSelfLink(uriInfo);
        return Response.ok(entity).links(self).build();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    static <T> Response getEntityResponse(Optional<T> optional, UriInfo uriInfo) {
        if (optional.isPresent()) {
            return getEntityResponse(optional.get(), uriInfo);
        }
        throw new WebApplicationException(NOT_FOUND);
    }

    static <T> Response getEntityResponse(List<T> entities, int start, int count, UriInfo uriInfo) {
        final var paginationLinks = Links.getPaginationLinks(start, count, entities.size(), uriInfo);
        return Response.ok(toEntity(entities)).links(paginationLinks).build();
    }

    static <T, I> Response getCreatedResponse(T entity, I id, UriInfo uriInfo) {
        final var location = Links.getLocation(uriInfo, id);
        return Response.created(location).entity(entity).build();
    }

    static Response getNoContentResponse() {
        return Response.noContent().build();
    }

    static <T> GenericEntity<List<T>> toEntity(final List<T> list) {
        return new GenericEntity<>(list) {
        };
    }
}
