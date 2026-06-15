package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.ExistingPatientsDTO;

import java.util.List;

public interface PatientRegistrationService {

    List<ExistingPatientsDTO> getPatients(String patientName);
}
