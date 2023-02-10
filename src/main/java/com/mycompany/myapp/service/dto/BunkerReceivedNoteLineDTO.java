package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BunkerReceivedNoteLine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BunkerReceivedNoteLineDTO implements Serializable {

    private Long id;

    private BigDecimal quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BunkerReceivedNoteLineDTO)) {
            return false;
        }

        BunkerReceivedNoteLineDTO bunkerReceivedNoteLineDTO = (BunkerReceivedNoteLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bunkerReceivedNoteLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BunkerReceivedNoteLineDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
