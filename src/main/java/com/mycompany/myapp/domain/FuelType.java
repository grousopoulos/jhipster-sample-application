package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.FuelTypeCode;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelType.
 */
@Entity
@Table(name = "fuel_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type_code", nullable = false)
    private FuelTypeCode fuelTypeCode;

    @NotNull
    @Column(name = "carbon_factory", precision = 21, scale = 2, nullable = false)
    private BigDecimal carbonFactory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FuelType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FuelTypeCode getFuelTypeCode() {
        return this.fuelTypeCode;
    }

    public FuelType fuelTypeCode(FuelTypeCode fuelTypeCode) {
        this.setFuelTypeCode(fuelTypeCode);
        return this;
    }

    public void setFuelTypeCode(FuelTypeCode fuelTypeCode) {
        this.fuelTypeCode = fuelTypeCode;
    }

    public BigDecimal getCarbonFactory() {
        return this.carbonFactory;
    }

    public FuelType carbonFactory(BigDecimal carbonFactory) {
        this.setCarbonFactory(carbonFactory);
        return this;
    }

    public void setCarbonFactory(BigDecimal carbonFactory) {
        this.carbonFactory = carbonFactory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelType)) {
            return false;
        }
        return id != null && id.equals(((FuelType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fuelTypeCode='" + getFuelTypeCode() + "'" +
            ", carbonFactory=" + getCarbonFactory() +
            "}";
    }
}
