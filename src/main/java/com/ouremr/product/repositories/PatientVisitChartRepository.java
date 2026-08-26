package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientVisitChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientVisitChartRepository extends JpaRepository<PatientVisitChart, Long> {
    Optional<PatientVisitChart> findByPatientVisitChartAppointmentId(Long appointmentId);
    List<PatientVisitChart> findByPatientVisitChartPatientIdOrderByPatientVisitChartCreatedOnDesc(Long patientId);
    Optional<PatientVisitChart> findByEncounterId(Long encounterId);
}
