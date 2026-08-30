package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientSymptoms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientSymptomsRepository extends JpaRepository<PatientSymptoms, Long> {
    Optional<PatientSymptoms> findByEncounter_EncounterId(Long encounterId);
}
