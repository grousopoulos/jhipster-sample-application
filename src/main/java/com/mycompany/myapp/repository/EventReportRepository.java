package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EventReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventReportRepository extends JpaRepository<EventReport, Long> {}
