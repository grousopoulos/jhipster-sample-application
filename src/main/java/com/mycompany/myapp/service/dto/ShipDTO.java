package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Ship} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShipDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String classificationSociety;

    private String iceClassPolarCode;

    private String technicalEfficiencyCode;

    private String shipType;

    private CountryDTO ownerCountry;

    private FlagDTO flag;

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

    public String getClassificationSociety() {
        return classificationSociety;
    }

    public void setClassificationSociety(String classificationSociety) {
        this.classificationSociety = classificationSociety;
    }

    public String getIceClassPolarCode() {
        return iceClassPolarCode;
    }

    public void setIceClassPolarCode(String iceClassPolarCode) {
        this.iceClassPolarCode = iceClassPolarCode;
    }

    public String getTechnicalEfficiencyCode() {
        return technicalEfficiencyCode;
    }

    public void setTechnicalEfficiencyCode(String technicalEfficiencyCode) {
        this.technicalEfficiencyCode = technicalEfficiencyCode;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public CountryDTO getOwnerCountry() {
        return ownerCountry;
    }

    public void setOwnerCountry(CountryDTO ownerCountry) {
        this.ownerCountry = ownerCountry;
    }

    public FlagDTO getFlag() {
        return flag;
    }

    public void setFlag(FlagDTO flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipDTO)) {
            return false;
        }

        ShipDTO shipDTO = (ShipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", classificationSociety='" + getClassificationSociety() + "'" +
            ", iceClassPolarCode='" + getIceClassPolarCode() + "'" +
            ", technicalEfficiencyCode='" + getTechnicalEfficiencyCode() + "'" +
            ", shipType='" + getShipType() + "'" +
            ", ownerCountry=" + getOwnerCountry() +
            ", flag=" + getFlag() +
            "}";
    }
}
