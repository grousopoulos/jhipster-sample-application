package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EventReportLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReportLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventReportLineRepository extends JpaRepository<EventReportLine, Long> {}
