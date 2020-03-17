package org.acme.example.documents.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENT")
@NamedQueries({
        @NamedQuery(name = "Document.findAll", query = "SELECT e FROM Document e"),
        @NamedQuery(name = "Document.countAll", query = "SELECT count(e) FROM Document e")
})
public class Document implements Serializable {

    @Id
    @NotNull
    private UUID id;

    @NotNull
    private String name;

    void update(Document document) {
        this.name = document.getName();
    }
}
