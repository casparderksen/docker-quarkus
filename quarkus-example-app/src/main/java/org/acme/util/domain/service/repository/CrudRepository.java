package org.acme.util.domain.service.repository;

import org.acme.example.documents.domain.model.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID extends Serializable> {

    T create(T entity);

    T update(T entity);

    Optional<T> findById(ID primaryKey);

    List<T> findAll();

    List<Document> findRange(int offset, int limit);

    Long count();

    void deleteById(ID primaryKey);
}