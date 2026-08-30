package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientVitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientVitalsRepository extends JpaRepository<PatientVitals, Long> {
    List<PatientVitals> findByPatientIdOrderByPatientVitalsCreatedOnDesc(Long patientId);
    List<PatientVitals> findByEncounter_EncounterId(Long encounterId);
}
