package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {}
