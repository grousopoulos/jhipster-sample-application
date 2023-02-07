package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Voyage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {}
