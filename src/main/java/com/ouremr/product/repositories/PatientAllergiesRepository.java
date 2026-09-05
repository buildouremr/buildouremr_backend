package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientAllergies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAllergiesRepository extends JpaRepository<PatientAllergies, Long> {

    @Query("SELECT pa FROM PatientAllergies pa WHERE pa.patientId = :patientId ORDER BY pa.patientAllergiesCreatedOn DESC")
    List<PatientAllergies> findByPatientIdOrderByPatientAllergiesCreatedOnDesc(@Param("patientId") Long patientId);
}
