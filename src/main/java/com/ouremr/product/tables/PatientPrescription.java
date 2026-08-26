package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_prescription")
public class PatientPrescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_prescription_id")
    private Long patientPrescriptionId;

    @Column(name = "patient_visit_chart_id")
    private Long patientVisitChartId;

    @Column(name = "encounter_id")
    private Long encounterId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "medication_id")
    private Long medicationId;

    @Column(name = "patient_prescription_drug_name")
    private String patientPrescriptionDrugName;

    @Column(name = "patient_prescription_frequency")
    private String patientPrescriptionFrequency;

    @Column(name = "patient_prescription_duration")
    private String patientPrescriptionDuration;

    @Column(name = "patient_prescription_instruction")
    private String patientPrescriptionInstruction;

    @Column(name = "patient_prescription_created_on")
    private LocalDateTime patientPrescriptionCreatedOn;

    @Column(name = "patient_prescription_created_by")
    private String patientPrescriptionCreatedBy;

    @Column(name = "patient_prescription_modified_on")
    private LocalDateTime patientPrescriptionModifiedOn;

    @Column(name = "patient_prescription_modified_by")
    private String patientPrescriptionModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_visit_chart_id", referencedColumnName = "patient_visit_chart_id", insertable = false, updatable = false)
    private PatientVisitChart patientVisitChart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", insertable = false, updatable = false)
    private Medication medication;

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

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getPatientPrescriptionDrugName() {
        return patientPrescriptionDrugName;
    }

    public void setPatientPrescriptionDrugName(String patientPrescriptionDrugName) {
        this.patientPrescriptionDrugName = patientPrescriptionDrugName;
    }

    public String getPatientPrescriptionFrequency() {
        return patientPrescriptionFrequency;
    }

    public void setPatientPrescriptionFrequency(String patientPrescriptionFrequency) {
        this.patientPrescriptionFrequency = patientPrescriptionFrequency;
    }

    public String getPatientPrescriptionDuration() {
        return patientPrescriptionDuration;
    }

    public void setPatientPrescriptionDuration(String patientPrescriptionDuration) {
        this.patientPrescriptionDuration = patientPrescriptionDuration;
    }

    public String getPatientPrescriptionInstruction() {
        return patientPrescriptionInstruction;
    }

    public void setPatientPrescriptionInstruction(String patientPrescriptionInstruction) {
        this.patientPrescriptionInstruction = patientPrescriptionInstruction;
    }

    public LocalDateTime getPatientPrescriptionCreatedOn() {
        return patientPrescriptionCreatedOn;
    }

    public void setPatientPrescriptionCreatedOn(LocalDateTime patientPrescriptionCreatedOn) {
        this.patientPrescriptionCreatedOn = patientPrescriptionCreatedOn;
    }

    public String getPatientPrescriptionCreatedBy() {
        return patientPrescriptionCreatedBy;
    }

    public void setPatientPrescriptionCreatedBy(String patientPrescriptionCreatedBy) {
        this.patientPrescriptionCreatedBy = patientPrescriptionCreatedBy;
    }

    public LocalDateTime getPatientPrescriptionModifiedOn() {
        return patientPrescriptionModifiedOn;
    }

    public void setPatientPrescriptionModifiedOn(LocalDateTime patientPrescriptionModifiedOn) {
        this.patientPrescriptionModifiedOn = patientPrescriptionModifiedOn;
    }

    public String getPatientPrescriptionModifiedBy() {
        return patientPrescriptionModifiedBy;
    }

    public void setPatientPrescriptionModifiedBy(String patientPrescriptionModifiedBy) {
        this.patientPrescriptionModifiedBy = patientPrescriptionModifiedBy;
    }

    public PatientVisitChart getPatientVisitChart() {
        return patientVisitChart;
    }

    public void setPatientVisitChart(PatientVisitChart patientVisitChart) {
        this.patientVisitChart = patientVisitChart;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
