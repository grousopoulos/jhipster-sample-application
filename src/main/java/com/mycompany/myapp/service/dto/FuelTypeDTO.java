package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.FuelTypeCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.FuelType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private FuelTypeCode fuelTypeCode;

    @NotNull
    private BigDecimal carbonFactory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FuelTypeCode getFuelTypeCode() {
        return fuelTypeCode;
    }

    public void setFuelTypeCode(FuelTypeCode fuelTypeCode) {
        this.fuelTypeCode = fuelTypeCode;
    }

    public BigDecimal getCarbonFactory() {
        return carbonFactory;
    }

    public void setCarbonFactory(BigDecimal carbonFactory) {
        this.carbonFactory = carbonFactory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelTypeDTO)) {
            return false;
        }

        FuelTypeDTO fuelTypeDTO = (FuelTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fuelTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fuelTypeCode='" + getFuelTypeCode() + "'" +
            ", carbonFactory=" + getCarbonFactory() +
            "}";
    }
}
