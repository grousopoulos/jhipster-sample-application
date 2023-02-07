package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FuelType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {}
