package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientDiagnosisNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientDiagnosisNotesRepository extends JpaRepository<PatientDiagnosisNotes, Long> {
    java.util.List<PatientDiagnosisNotes> findByEncounter_EncounterIdAndIsActiveTrue(Long encounterId);
    java.util.List<PatientDiagnosisNotes> findTop50ByOrderByPatientDiagnosisNotesModifiedOnDesc();
}
