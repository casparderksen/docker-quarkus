package org.acme.util.domain.service.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, I extends Serializable> {

    T create(T entity);

    T update(T entity);

    Optional<T> findById(I id);

    List<T> findAll();

    List<T> findRange(int offset, int limit);

    Long count();

    void deleteById(I id);
}