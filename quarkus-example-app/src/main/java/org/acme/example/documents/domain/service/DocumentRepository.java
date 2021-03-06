package org.acme.example.documents.domain.service;

import org.acme.example.documents.domain.model.Document;
import org.acme.util.domain.service.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends CrudRepository<Document, Long> {

    Document findByUuid(UUID uuid);

    Document updateByUuid(UUID uuid, Document document);

    void deleteByUuid(UUID uuid);
}
