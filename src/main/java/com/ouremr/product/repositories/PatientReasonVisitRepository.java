package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientReasonVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientReasonVisitRepository extends JpaRepository<PatientReasonVisit, Long> {
    Optional<PatientReasonVisit> findByEncounter_EncounterIdAndPatient_PatientRegistrationId(Long encounterId, Long patientId);
}
