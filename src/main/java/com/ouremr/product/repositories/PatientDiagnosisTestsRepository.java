package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientDiagnosisTests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientDiagnosisTestsRepository extends JpaRepository<PatientDiagnosisTests, Long> {
    Optional<PatientDiagnosisTests> findByEncounter_EncounterId(Long encounterId);
}
