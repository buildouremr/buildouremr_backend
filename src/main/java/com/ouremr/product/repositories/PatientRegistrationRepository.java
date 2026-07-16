package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatientRegistrationRepository extends JpaRepository<PatientRegistration, Long>, JpaSpecificationExecutor<PatientRegistration> {
}
