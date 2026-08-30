package com.ouremr.product.dto;

public class PatientVitalsDTO {

    private Long patientVitalsId;
    private Long patientVisitChartId;
    private java.util.Map<String, Object> patientVitalData;

    public Long getPatientVitalsId() {
        return patientVitalsId;
    }

    public void setPatientVitalsId(Long patientVitalsId) {
        this.patientVitalsId = patientVitalsId;
    }

    public Long getPatientVisitChartId() {
        return patientVisitChartId;
    }

    public void setPatientVisitChartId(Long patientVisitChartId) {
        this.patientVisitChartId = patientVisitChartId;
    }

    public java.util.Map<String, Object> getPatientVitalData() {
        return patientVitalData;
    }

    public void setPatientVitalData(java.util.Map<String, Object> patientVitalData) {
        this.patientVitalData = patientVitalData;
    }
}
