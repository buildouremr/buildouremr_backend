package com.ouremr.product.repositories;

import com.ouremr.product.tables.Encounter;
import com.ouremr.product.tables.PatientRegistration;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Encounter> findByPatientAndEncounterIsCompletedFalse(PatientRegistration patient);
}
