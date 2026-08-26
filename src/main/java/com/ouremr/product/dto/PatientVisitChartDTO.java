package com.ouremr.product.dto;

import java.util.List;

public class PatientVisitChartDTO {

    private Long patientVisitChartId;
    private Long patientId;
    private Long appointmentId;

    // Demographics and Info pulled from PatientRegistration
    private String patientName;
    private String patientGender;
    private String patientAge;
    private String patientDob;
    private String patientBloodGroup;
    private String patientHeight;
    private String patientWeight;
    private String patientBmi;
    private String patientAllergies;
    private String patientChronicConditions;
    private String patientRiskFactors;
    private String patientInsurance;

    // Chart Details
    private String reasonForVisit;
    private String symptoms;
    private String examination;
    private String diagnosisTests;
    private String diagnosisNotes;
    private String treatmentPlan;
    private String advice;
    private String status;

    private List<PatientVitalsDTO> vitals;
    private List<PatientPrescriptionDTO> prescriptions;

    public Long getPatientVisitChartId() {
        return patientVisitChartId;
    }

    public void setPatientVisitChartId(Long patientVisitChartId) {
        this.patientVisitChartId = patientVisitChartId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientDob() {
        return patientDob;
    }

    public void setPatientDob(String patientDob) {
        this.patientDob = patientDob;
    }

    public String getPatientBloodGroup() {
        return patientBloodGroup;
    }

    public void setPatientBloodGroup(String patientBloodGroup) {
        this.patientBloodGroup = patientBloodGroup;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientBmi() {
        return patientBmi;
    }

    public void setPatientBmi(String patientBmi) {
        this.patientBmi = patientBmi;
    }

    public String getPatientAllergies() {
        return patientAllergies;
    }

    public void setPatientAllergies(String patientAllergies) {
        this.patientAllergies = patientAllergies;
    }

    public String getPatientChronicConditions() {
        return patientChronicConditions;
    }

    public void setPatientChronicConditions(String patientChronicConditions) {
        this.patientChronicConditions = patientChronicConditions;
    }

    public String getPatientRiskFactors() {
        return patientRiskFactors;
    }

    public void setPatientRiskFactors(String patientRiskFactors) {
        this.patientRiskFactors = patientRiskFactors;
    }

    public String getPatientInsurance() {
        return patientInsurance;
    }

    public void setPatientInsurance(String patientInsurance) {
        this.patientInsurance = patientInsurance;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getExamination() {
        return examination;
    }

    public void setExamination(String examination) {
        this.examination = examination;
    }

    public String getDiagnosisTests() {
        return diagnosisTests;
    }

    public void setDiagnosisTests(String diagnosisTests) {
        this.diagnosisTests = diagnosisTests;
    }

    public String getDiagnosisNotes() {
        return diagnosisNotes;
    }

    public void setDiagnosisNotes(String diagnosisNotes) {
        this.diagnosisNotes = diagnosisNotes;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PatientVitalsDTO> getVitals() {
        return vitals;
    }

    public void setVitals(List<PatientVitalsDTO> vitals) {
        this.vitals = vitals;
    }

    public List<PatientPrescriptionDTO> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PatientPrescriptionDTO> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
