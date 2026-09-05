package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientPrescriptionRepository extends JpaRepository<PatientPrescription, Long> {

    @Query("SELECT p FROM PatientPrescription p WHERE p.patientPrescriptionEncounterId = :encounterId AND p.isActive = true")
    List<PatientPrescription> findByPatientPrescriptionEncounterIdAndIsActiveTrue(@Param("encounterId") Long encounterId);

    @Query("DELETE FROM PatientPrescription p WHERE p.patientPrescriptionEncounterId = :encounterId")
    void deleteByPatientPrescriptionEncounterId(@Param("encounterId") Long encounterId);

    @Query("SELECT p FROM PatientPrescription p WHERE p.patientPrescriptionPatientId = :patientId AND p.isActive = true ORDER BY p.patientPrescriptionCreatedOn DESC")
    List<PatientPrescription> findActiveByPatientId(@Param("patientId") Long patientId);
}
