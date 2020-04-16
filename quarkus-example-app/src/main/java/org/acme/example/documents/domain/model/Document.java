package org.acme.example.documents.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.acme.util.domain.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "DOCUMENT")
@NamedQueries({
        @NamedQuery(name = "Document.findId", query = "SELECT e.id FROM Document e WHERE e.uuid = :uuid"),
        @NamedQuery(name = "Document.findAll", query = "SELECT e FROM Document e ORDER BY e.id"),
        @NamedQuery(name = "Document.countAll", query = "SELECT count(e) FROM Document e")
})
public class Document extends BaseEntity {

    @NotNull
    @Column(updatable = false, nullable = false)
    private UUID uuid;

    @NotNull
    private String name;
}
