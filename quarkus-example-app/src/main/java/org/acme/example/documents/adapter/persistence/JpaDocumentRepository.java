package org.acme.example.documents.adapter.persistence;

import org.acme.example.documents.domain.model.Document;
import org.acme.example.documents.domain.service.DocumentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class JpaDocumentRepository implements DocumentRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public Document create(Document document) {
        entityManager.persist(document);
        return document;
    }

    @Override
    public Document update(Document document) {
        // Throws exception if entity not found
        entityManager.getReference(Document.class, document.getId());
        return entityManager.merge(document);
    }

    @Override
    public Optional<Document> findById(UUID uuid) {
        var entity = entityManager.find(Document.class, uuid);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<Document> findAll() {
        var query = entityManager.createNamedQuery("Document.findAll", Document.class);
        return query.getResultList();
    }

    public List<Document> findRange(int offset, int limit) {
        var query = entityManager.createNamedQuery("Document.findAll", Document.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public Long count() {
        var query = entityManager.createNamedQuery("Document.countAll", Long.class);
        return query.getSingleResult();
    }

    @Override
    public void deleteById(UUID id) {
        var entity = entityManager.getReference(Document.class, id);
        entityManager.remove(entity);
    }
}
