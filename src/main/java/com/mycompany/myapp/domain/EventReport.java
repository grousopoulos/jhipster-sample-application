package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EventReport.
 */
@Entity
@Table(name = "event_report")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_date_and_time", nullable = false)
    private ZonedDateTime documentDateAndTime;

    @Column(name = "speed_gps", precision = 21, scale = 2)
    private BigDecimal speedGps;

    @Column(name = "document_display_number")
    private String documentDisplayNumber;

    @ManyToOne
    private Voyage voyage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventReport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return this.documentDateAndTime;
    }

    public EventReport documentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.setDocumentDateAndTime(documentDateAndTime);
        return this;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public BigDecimal getSpeedGps() {
        return this.speedGps;
    }

    public EventReport speedGps(BigDecimal speedGps) {
        this.setSpeedGps(speedGps);
        return this;
    }

    public void setSpeedGps(BigDecimal speedGps) {
        this.speedGps = speedGps;
    }

    public String getDocumentDisplayNumber() {
        return this.documentDisplayNumber;
    }

    public EventReport documentDisplayNumber(String documentDisplayNumber) {
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

    public EventReport voyage(Voyage voyage) {
        this.setVoyage(voyage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReport)) {
            return false;
        }
        return id != null && id.equals(((EventReport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReport{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", speedGps=" + getSpeedGps() +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            "}";
    }
}
