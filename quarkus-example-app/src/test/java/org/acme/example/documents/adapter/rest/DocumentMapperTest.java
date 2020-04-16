package org.acme.example.documents.adapter.rest;

import org.acme.example.documents.domain.model.Document;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentMapperTest {

    @Test
    void shouldMapDtoToDocument() {
        var uuid = UUID.randomUUID();
        var name = "foo";
        var dto = DocumentMessage.builder().uuid(uuid.toString()).name(name).build();
        var document = DocumentMapper.INSTANCE.toDocument(dto);
        assertThat(document.getId()).isNull();
        assertThat(document.getUuid()).isEqualTo(uuid);
        assertThat(document.getName()).isEqualTo(name);
    }

    @Test
    void shouldMapDocumentToDto() {
        var uuid = UUID.randomUUID();
        var name = "foo";
        Document document = new Document();
        document.setUuid(uuid);
        document.setName(name);
        var dto = DocumentMapper.INSTANCE.fromDocument(document);
        assertThat(dto.getUuid()).isEqualTo(uuid.toString());
        assertThat(dto.getName()).isEqualTo(name);
    }
}