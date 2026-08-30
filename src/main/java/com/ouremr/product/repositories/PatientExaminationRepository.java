package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientExaminationRepository extends JpaRepository<PatientExamination, Long> {
    Optional<PatientExamination> findByEncounter_EncounterId(Long encounterId);
}
