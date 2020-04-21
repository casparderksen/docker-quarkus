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
        document.setUuid(UUID.randomUUID());
        entityManager.persist(document);
        return document;
    }

    @Override
    public Document update(Document document) {
        // Throws EntityNotFoundException if not found
        entityManager.getReference(Document.class, document.getId());
        return entityManager.merge(document);
    }

    @Override
    public Optional<Document> findById(Long id) {
        var entity = entityManager.find(Document.class, id);
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
    public void deleteById(Long id) {
        var entity = entityManager.getReference(Document.class, id);
        entityManager.remove(entity);
    }

    @Override
    public Document findByUuid(UUID uuid) {
        var id = findDocumentId(uuid);
        return findById(id).get();
    }

    @Override
    public Document updateByUuid(UUID uuid, Document document) {
        var id = findDocumentId(uuid);
        document.setId(id);
        document.setUuid(uuid);
        return update(document);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        var id = findDocumentId(uuid);
        deleteById(id);
    }

    private Long findDocumentId(UUID uuid) {
        var query = entityManager.createNamedQuery("Document.findId", Long.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }
}
