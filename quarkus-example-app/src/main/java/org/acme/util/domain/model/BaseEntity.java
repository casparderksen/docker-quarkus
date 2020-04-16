package org.acme.util.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.model.relational.Sequence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGenerator")
    @SequenceGenerator(name="IdGenerator", sequenceName = "id_seq", allocationSize=50)
    @Column(updatable = false, nullable = false)
    protected Long id;
}
