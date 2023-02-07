package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ship.
 */
@Entity
@Table(name = "ship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "classification_society")
    private String classificationSociety;

    @Column(name = "ice_class_polar_code")
    private String iceClassPolarCode;

    @Column(name = "technical_efficiency_code")
    private String technicalEfficiencyCode;

    @Column(name = "ship_type")
    private String shipType;

    @ManyToOne
    private Country ownerCountry;

    @ManyToOne
    private Flag flag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ship id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ship name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassificationSociety() {
        return this.classificationSociety;
    }

    public Ship classificationSociety(String classificationSociety) {
        this.setClassificationSociety(classificationSociety);
        return this;
    }

    public void setClassificationSociety(String classificationSociety) {
        this.classificationSociety = classificationSociety;
    }

    public String getIceClassPolarCode() {
        return this.iceClassPolarCode;
    }

    public Ship iceClassPolarCode(String iceClassPolarCode) {
        this.setIceClassPolarCode(iceClassPolarCode);
        return this;
    }

    public void setIceClassPolarCode(String iceClassPolarCode) {
        this.iceClassPolarCode = iceClassPolarCode;
    }

    public String getTechnicalEfficiencyCode() {
        return this.technicalEfficiencyCode;
    }

    public Ship technicalEfficiencyCode(String technicalEfficiencyCode) {
        this.setTechnicalEfficiencyCode(technicalEfficiencyCode);
        return this;
    }

    public void setTechnicalEfficiencyCode(String technicalEfficiencyCode) {
        this.technicalEfficiencyCode = technicalEfficiencyCode;
    }

    public String getShipType() {
        return this.shipType;
    }

    public Ship shipType(String shipType) {
        this.setShipType(shipType);
        return this;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public Country getOwnerCountry() {
        return this.ownerCountry;
    }

    public void setOwnerCountry(Country country) {
        this.ownerCountry = country;
    }

    public Ship ownerCountry(Country country) {
        this.setOwnerCountry(country);
        return this;
    }

    public Flag getFlag() {
        return this.flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Ship flag(Flag flag) {
        this.setFlag(flag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ship)) {
            return false;
        }
        return id != null && id.equals(((Ship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ship{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", classificationSociety='" + getClassificationSociety() + "'" +
            ", iceClassPolarCode='" + getIceClassPolarCode() + "'" +
            ", technicalEfficiencyCode='" + getTechnicalEfficiencyCode() + "'" +
            ", shipType='" + getShipType() + "'" +
            "}";
    }
}
