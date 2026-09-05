package com.ouremr.product.patientchart;

import com.ouremr.product.dto.PatientVisitChartDTO;
import java.util.List;

public interface PatientChartService {
    PatientVisitChartDTO getChartByAppointmentId(Long appointmentId);
    PatientVisitChartDTO saveChart(PatientVisitChartDTO chartDTO);
    List<PatientVisitChartDTO> getPatientChartHistory(Long patientId);
    PatientVisitChartDTO getPatientSnapshot(Long patientId, Long encounterId);
    com.ouremr.product.dto.PatientProfileDTO getPatientProfile(Long patientId);
    com.ouremr.product.dto.PatientHeaderDTO getPatientHeader(Long patientId);
    PatientVisitChartDTO getChartByEncounterId(Long patientId, Long encounterId);
    void savePatientAllergies(Long patientId, java.util.Map<String, Object> allergiesData);
    void savePatientChronicConditions(Long patientId, java.util.Map<String, Object> chronicConditionsData);
}
