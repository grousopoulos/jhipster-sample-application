package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.UnitOfMeasure;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EventReportLine} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventReportLineDTO implements Serializable {

    private Long id;

    private BigDecimal quantity;

    private UnitOfMeasure unitOfMeasure;

    private FuelTypeDTO fuelType;

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

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public FuelTypeDTO getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelTypeDTO fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventReportLineDTO)) {
            return false;
        }

        EventReportLineDTO eventReportLineDTO = (EventReportLineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventReportLineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventReportLineDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unitOfMeasure='" + getUnitOfMeasure() + "'" +
            ", fuelType=" + getFuelType() +
            "}";
    }
}
