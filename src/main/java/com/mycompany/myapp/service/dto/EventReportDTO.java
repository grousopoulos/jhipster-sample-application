package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EventReport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReportDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime documentDateAndTime;

    private BigDecimal speedGps;

    private String documentDisplayNumber;

    private VoyageDTO voyage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDocumentDateAndTime() {
        return documentDateAndTime;
    }

    public void setDocumentDateAndTime(ZonedDateTime documentDateAndTime) {
        this.documentDateAndTime = documentDateAndTime;
    }

    public BigDecimal getSpeedGps() {
        return speedGps;
    }

    public void setSpeedGps(BigDecimal speedGps) {
        this.speedGps = speedGps;
    }

    public String getDocumentDisplayNumber() {
        return documentDisplayNumber;
    }

    public void setDocumentDisplayNumber(String documentDisplayNumber) {
        this.documentDisplayNumber = documentDisplayNumber;
    }

    public VoyageDTO getVoyage() {
        return voyage;
    }

    public void setVoyage(VoyageDTO voyage) {
        this.voyage = voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReportDTO)) {
            return false;
        }

        EventReportDTO eventReportDTO = (EventReportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventReportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReportDTO{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", speedGps=" + getSpeedGps() +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            ", voyage=" + getVoyage() +
            "}";
    }
}
