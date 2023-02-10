package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BunkerReceivedNote} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime documentDateAndTime;

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
        if (!(o instanceof BunkerReceivedNoteDTO)) {
            return false;
        }

        BunkerReceivedNoteDTO bunkerReceivedNoteDTO = (BunkerReceivedNoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bunkerReceivedNoteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteDTO{" +
            "id=" + getId() +
            ", documentDateAndTime='" + getDocumentDateAndTime() + "'" +
            ", documentDisplayNumber='" + getDocumentDisplayNumber() + "'" +
            ", voyage=" + getVoyage() +
            "}";
    }
}
