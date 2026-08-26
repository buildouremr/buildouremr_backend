package com.ouremr.product.dto;

public class PatientPrescriptionDTO {

    private Long patientPrescriptionId;
    private Long patientVisitChartId;
    private Long medicationId;
    private String drugName;
    private String frequency;
    private String duration;
    private String instruction;

    public Long getPatientPrescriptionId() {
        return patientPrescriptionId;
    }

    public void setPatientPrescriptionId(Long patientPrescriptionId) {
        this.patientPrescriptionId = patientPrescriptionId;
    }

    public Long getPatientVisitChartId() {
        return patientVisitChartId;
    }

    public void setPatientVisitChartId(Long patientVisitChartId) {
        this.patientVisitChartId = patientVisitChartId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
