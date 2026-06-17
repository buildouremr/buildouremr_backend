package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.ExistingPatientsDTO;
import com.ouremr.product.dto.PatientDetailsDTO;

import java.util.List;

public interface PatientRegistrationService {

    List<ExistingPatientsDTO> getPatients(String patientName);

    List<PatientDetailsDTO> getAllPatients();
}
