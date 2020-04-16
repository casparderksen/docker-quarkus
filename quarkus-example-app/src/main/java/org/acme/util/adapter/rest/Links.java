package org.acme.util.adapter.rest;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public interface Links {
    String NEXT = "next";
    String PREV = "prev";
    String SELF = "self";
    String OFFSET = "offset";
    String LIMIT = "limit";

    static Link getSelfLink(UriInfo uriInfo) {
        final var uri = uriInfo.getAbsolutePath();
        return Link.fromUri(uri).rel(SELF).build();
    }

    static Link[] getPaginationLinks(Range range, int count, UriInfo uriInfo) {
        int offset = range.getOffset();
        int limit = range.getLimit();
        final List<Link> links = new ArrayList<>();
        if (limit <= count) {
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
