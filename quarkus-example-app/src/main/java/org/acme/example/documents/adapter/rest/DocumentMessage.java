package org.acme.example.documents.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.util.domain.service.validation.ValidUUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Document", description = "Datatype that represents documents")
public class DocumentMessage implements Serializable {

    @Schema(description = "UUID of the document")
    @ValidUUID
    private String uuid;

    @Schema(description = "Description of the document", required = true)
    private String name;
}
