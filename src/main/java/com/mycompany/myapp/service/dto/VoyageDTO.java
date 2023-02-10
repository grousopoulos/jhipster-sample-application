package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Voyage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoyageDTO implements Serializable {

    private Long id;

    @NotNull
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoyageDTO)) {
            return false;
        }

        VoyageDTO voyageDTO = (VoyageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voyageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoyageDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
