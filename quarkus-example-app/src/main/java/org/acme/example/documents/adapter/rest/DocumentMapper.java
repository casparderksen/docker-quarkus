package org.acme.example.documents.adapter.rest;

import org.acme.example.documents.domain.model.Document;
import org.acme.util.adapter.mapstruct.UUIDMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UUIDMapper.class)
interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "id", ignore = true)
    Document toDocument(DocumentMessage dto);

    DocumentMessage fromDocument(Document document);
}
