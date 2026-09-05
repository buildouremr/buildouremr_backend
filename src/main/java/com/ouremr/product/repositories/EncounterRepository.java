package com.ouremr.product.repositories;

import com.ouremr.product.tables.Encounter;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Encounter e WHERE e.patient = :patient AND e.encounterIsCompleted = false")
    Optional<Encounter> findByPatientAndEncounterIsCompletedFalse(@Param("patient") PatientRegistration patient);

    @Query("SELECT COUNT(e) FROM Encounter e WHERE e.patient.patientRegistrationId = :patientId")
    Long countByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT e FROM Encounter e WHERE e.patient.patientRegistrationId = :patientId ORDER BY e.encounterCreatedOn DESC LIMIT 1")
    Optional<Encounter> findLatestByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT e FROM Encounter e WHERE e.patient.patientRegistrationId = :patientId ORDER BY e.encounterCreatedOn DESC")
    java.util.List<Encounter> findAllByPatientIdDesc(@Param("patientId") Long patientId);
}
