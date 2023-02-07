package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClasificationSociety;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClasificationSociety entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasificationSocietyRepository extends JpaRepository<ClasificationSociety, Long> {}
