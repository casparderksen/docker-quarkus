package org.acme.util.adapter.rest;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public interface Links {
    String NEXT = "next";
    String PREV = "prev";
    String SELF = "self";
    String OFFSET = "offset";
    String LIMIT = "limit";

    static <T> URI getLocation(UriInfo uriInfo, T id) {
        return uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
    }

    static Link getSelfLink(UriInfo uriInfo) {
        final var uri = uriInfo.getAbsolutePath();
        return Link.fromUri(uri).rel(SELF).build();
    }

    static Link[] getPaginationLinks(int offset, int limit, int nrAvailable, UriInfo uriInfo) {
        final List<Link> links = new ArrayList<>();
        if (limit <= nrAvailable) {
            final var next = uriInfo.getAbsolutePathBuilder()
                    .queryParam(OFFSET, offset + limit)
                    .queryParam(LIMIT, limit).build();
            links.add(Link.fromUri(next).rel(NEXT).build());
        }
        if (offset > 0) {
            final var prev = uriInfo.getAbsolutePathBuilder()
                    .queryParam(OFFSET, Integer.max(offset - limit, 0))
                    .queryParam(LIMIT, limit).build();
            links.add(Link.fromUri(prev).rel(PREV).build());
        }
        return links.toArray(new Link[0]);
    }
}
