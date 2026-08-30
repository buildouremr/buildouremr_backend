package com.ouremr.product.repositories;

import com.ouremr.product.tables.Chart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Chart, Long> {
    Optional<Chart> findByPatientId(Long patientId);
}
