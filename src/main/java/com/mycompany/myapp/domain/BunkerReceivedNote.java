package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BunkerReceivedNote.
 */
@Entity
@Table(name = "bunker_received_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_date_and_time", nullable = false)
    private ZonedDateTime documentDateAndTime;

    @Column(name = "document_display_number")
    private String documentDisplayNumber;

    @ManyToOne
    private Voyage voyage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BunkerReceivedNote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return this.documentDateAndTime;
    }

    public BunkerReceivedNote documentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.setDocumentDateAndTime(documentDateAndTime);
        return this;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public String getDocumentDisplayNumber() {
        return this.documentDisplayNumber;
    }

    public BunkerReceivedNote documentDisplayNumber(String documentDisplayNumber) {
        this.setDocumentDisplayNumber(documentDisplayNumber);
        return this;
    }

    public void setDocumentDisplayNumber(String documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public Voyage getVoyage() {
        return this.voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public BunkerReceivedNote voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BunkerReceivedNote)) {
            return false;
        }
        return id != null && id.equals(((BunkerReceivedNote) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNote{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            "}";
    }
}
