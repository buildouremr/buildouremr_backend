package com.ouremr.product.patientchart;

import com.ouremr.product.dto.PatientVisitChartDTO;
import com.ouremr.product.dto.PatientVitalsDTO;
import java.util.List;

public interface PatientChartService {
    PatientVisitChartDTO getChartByAppointmentId(Long appointmentId);
    PatientVisitChartDTO saveChart(PatientVisitChartDTO chartDTO);
    List<PatientVisitChartDTO> getPatientChartHistory(Long patientId);
    PatientVisitChartDTO getPatientSnapshot(Long patientId);
    com.ouremr.product.dto.PatientProfileDTO getPatientProfile(Long patientId);
    void autoSaveSection(Long encounterId, String section, com.ouremr.product.dto.PatientChartDTO dto, Long userId);
    void saveVitals(Long encounterId, List<PatientVitalsDTO> vitals, Long userId);
}
