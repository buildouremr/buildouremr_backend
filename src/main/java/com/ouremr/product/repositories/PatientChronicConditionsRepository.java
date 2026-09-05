package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientChronicConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientChronicConditionsRepository extends JpaRepository<PatientChronicConditions, Long> {

    @Query("SELECT pcc FROM PatientChronicConditions pcc WHERE pcc.patientId = :patientId ORDER BY pcc.createdAt DESC LIMIT 1")
    Optional<PatientChronicConditions> findFirstByPatientIdOrderByCreatedAtDesc(@Param("patientId") Long patientId);
}
