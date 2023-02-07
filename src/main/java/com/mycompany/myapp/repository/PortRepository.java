package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Port;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Port entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortRepository extends JpaRepository<Port, Long> {}
