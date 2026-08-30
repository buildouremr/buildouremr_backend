package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientTreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientTreatmentPlanRepository extends JpaRepository<PatientTreatmentPlan, Long> {
    Optional<PatientTreatmentPlan> findByEncounter_EncounterId(Long encounterId);
}
