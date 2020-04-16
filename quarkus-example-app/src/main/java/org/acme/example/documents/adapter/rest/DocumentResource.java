package org.acme.example.documents.adapter.rest;

import org.acme.example.documents.domain.service.DocumentRepository;
import org.acme.util.adapter.rest.Links;
import org.acme.util.adapter.rest.Responses;
import org.acme.util.domain.service.validation.ValidUUID;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.links.Link;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
@Transactional
@Path("/documents")
@Tag(name = "Document resource", description = "CRUD service for Document entities")
public class DocumentResource {

    @Inject
    DocumentRepository documentRepository;

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getDocument", description = "Gets a document by id")
    @Parameter(name = "uuid", description = "id of the document", in = ParameterIn.PATH, required = true, schema = @Schema(type = SchemaType.STRING))
    @APIResponse(responseCode = "200",
            description = "Success, returns the value",
            content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentMessage.class)))
    @APIResponse(responseCode = "404", description = "Not found")
    public Response getDocument(@PathParam("uuid") @ValidUUID String uuid, @Context UriInfo uriInfo) {
        var document = documentRepository.findByUuid(UUID.fromString(uuid));
        var dto = DocumentMapper.INSTANCE.fromDocument(document);
        return Responses.getEntityResponse(dto, uriInfo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getDocuments", description = "Gets the collection of document (optionally paginated)")
    @Parameter(name = "offset", in = ParameterIn.PATH, schema = @Schema(type = SchemaType.INTEGER), description = "Start offset in collection")
    @Parameter(name = "limit", in = ParameterIn.PATH, schema = @Schema(type = SchemaType.INTEGER), description = "Max chunk size of returned values")
    @APIResponse(responseCode = "200",
            description = "Success, returns the collection",
            content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = DocumentMessage.class)),
            links = {@Link(name = "prev", description = "previous page", operationId = "getDocuments"),
                    @Link(name = "next", description = "next page", operationId = "getDocuments")})
    @APIResponse(responseCode = "400", description = "Bad request (invalid range)")
    public Response getDocuments(
            @QueryParam(Links.OFFSET) @DefaultValue("0") @Min(value = 0, message = "parameter 'offset' must be at least {value}") int offset,
            @QueryParam(Links.LIMIT) @DefaultValue("20") @Min(value = 1, message = "parameter 'limit' must be at least {value}") @Max(value = 100, message = "parameter 'limit' must be at most {value}") int limit,
            @Context UriInfo uriInfo) {
        final var documents = documentRepository.findRange(offset, limit);
        final var dtoList = documents.stream().map(DocumentMapper.INSTANCE::fromDocument).collect(toList());
        return Responses.getEntitiesResponse(dtoList, offset, limit, uriInfo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "createDocument", description = "Creates a new document")
    @APIResponse(responseCode = "201",
            description = "Success, returns the value",
            content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentMessage.class)))
    public Response createDocument(@RequestBody(description = "new document") DocumentMessage dto,
                                   @Context UriInfo uriInfo) {
        var document = documentRepository.create(DocumentMapper.INSTANCE.toDocument(dto));
        return Responses.getCreatedResponse(DocumentMapper.INSTANCE.fromDocument(document), document.getUuid(), uriInfo);
    }

    @PUT
    @Path("{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "updateDocument", description = "Updates the document with the specified id")
    @Parameter(name = "uuid", description = "id of the document", in = ParameterIn.PATH, required = true, schema = @Schema(type = SchemaType.STRING))
    @APIResponse(responseCode = "200",
            description = "Success, returns the new item",
            content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.OBJECT, implementation = DocumentMessage.class)))
    @APIResponse(responseCode = "404", description = "Not found")
    public Response updateDocument(@PathParam("uuid") @ValidUUID String uuid,
                                   @RequestBody(description = "updated document") DocumentMessage dto,
                                   @Context UriInfo uriInfo) {
        var document = documentRepository.updateByUuid(UUID.fromString(uuid), DocumentMapper.INSTANCE.toDocument(dto));
        return Responses.getEntityResponse(DocumentMapper.INSTANCE.fromDocument(document), uriInfo);
    }

    @DELETE
    @Path("{uuid}")
    @Operation(operationId = "deleteDocument", description = "Deletes the document with the specified id")
    @Parameter(name = "uuid", description = "id of the document", in = ParameterIn.PATH, required = true, schema = @Schema(type = SchemaType.STRING))
    @APIResponse(responseCode = "204", description = "Success, no content")
    @APIResponse(responseCode = "404", description = "Not found")
    public Response deleteDocument(@PathParam("uuid") @ValidUUID String uuid, @Context UriInfo uriInfo) {
        documentRepository.deleteByUuid(UUID.fromString(uuid));
        return Responses.getNoContentResponse();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "getCount", description = "Counts the number of documents")
    @APIResponse(responseCode = "200",
            description = "Success, return number of values",
            content = @Content(mediaType = "text/plain", schema = @Schema(type = SchemaType.INTEGER)))
    public long getCount(@Context UriInfo uriInfo) {
        return documentRepository.count();
    }
}
