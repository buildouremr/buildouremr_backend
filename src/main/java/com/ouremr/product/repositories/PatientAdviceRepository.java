package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientAdviceRepository extends JpaRepository<PatientAdvice, Long> {
    Optional<PatientAdvice> findByEncounter_EncounterId(Long encounterId);
}
