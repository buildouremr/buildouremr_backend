package com.ouremr.product.patientregistration;

import com.ouremr.product.dto.CreatePatientDTO;
import com.ouremr.product.dto.ExistingPatientsDTO;
import com.ouremr.product.dto.PatientDetailsDTO;
import com.ouremr.product.tables.ChronicDisease;

import java.util.List;

public interface PatientRegistrationService {

    List<ExistingPatientsDTO> getPatients(String patientName);

    List<PatientDetailsDTO> getAllPatients();

    List<ChronicDisease> getChronicDiseases();

    Boolean createNewPatient(CreatePatientDTO bean);
}
