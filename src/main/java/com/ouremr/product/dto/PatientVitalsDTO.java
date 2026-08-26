package com.ouremr.product.dto;

public class PatientVitalsDTO {

    private Long patientVitalsId;
    private Long patientVisitChartId;
    private String vitalHeader;
    private String vitalData;

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

    public String getVitalHeader() {
        return vitalHeader;
    }

    public void setVitalHeader(String vitalHeader) {
        this.vitalHeader = vitalHeader;
    }

    public String getVitalData() {
        return vitalData;
    }

    public void setVitalData(String vitalData) {
        this.vitalData = vitalData;
    }
}
