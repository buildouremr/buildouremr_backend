package com.ouremr.product.repositories;

import com.ouremr.product.tables.PatientRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PatientRegistrationRepository extends JpaRepository<PatientRegistration, Long>, JpaSpecificationExecutor<PatientRegistration> {
    List<PatientRegistration> findByPatientRegistrationFirstNameIgnoreCaseAndPatientRegistrationLastNameIgnoreCaseAndPatientRegistrationMobileNo(String firstName, String lastName, String mobileNo);
}
