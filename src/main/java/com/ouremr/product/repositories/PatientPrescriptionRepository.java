package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientPrescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientPrescriptionRepository extends JpaRepository<PatientPrescription, Long> {
    List<PatientPrescription> findByPatientVisitChartId(Long patientVisitChartId);
    void deleteByPatientVisitChartId(Long patientVisitChartId);
}
