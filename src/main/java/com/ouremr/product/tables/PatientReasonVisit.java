package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_reason_visit")
public class PatientReasonVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_reason_visit_id")
    private Long patientReasonVisitId;

    @Column(name = "patient_reason_visit_data", columnDefinition = "TEXT")
    private String patientReasonVisitData;

    @Column(name = "patient_reason_visit_created_on")
    private LocalDateTime patientReasonVisitCreatedOn;

    @Column(name = "patient_reason_visit_modified_on")
    private LocalDateTime patientReasonVisitModifiedOn;

    @Column(name = "patient_reason_visit_created_by")
    private String patientReasonVisitCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_reason_visit_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_reason_visit_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    // Getters and Setters

    public Long getPatientReasonVisitId() {
        return patientReasonVisitId;
    }

    public void setPatientReasonVisitId(Long patientReasonVisitId) {
        this.patientReasonVisitId = patientReasonVisitId;
    }

    public String getPatientReasonVisitData() {
        return patientReasonVisitData;
    }

    public void setPatientReasonVisitData(String patientReasonVisitData) {
        this.patientReasonVisitData = patientReasonVisitData;
    }

    public LocalDateTime getPatientReasonVisitCreatedOn() {
        return patientReasonVisitCreatedOn;
    }

    public void setPatientReasonVisitCreatedOn(LocalDateTime patientReasonVisitCreatedOn) {
        this.patientReasonVisitCreatedOn = patientReasonVisitCreatedOn;
    }

    public LocalDateTime getPatientReasonVisitModifiedOn() {
        return patientReasonVisitModifiedOn;
    }

    public void setPatientReasonVisitModifiedOn(LocalDateTime patientReasonVisitModifiedOn) {
        this.patientReasonVisitModifiedOn = patientReasonVisitModifiedOn;
    }

    public String getPatientReasonVisitCreatedBy() {
        return patientReasonVisitCreatedBy;
    }

    public void setPatientReasonVisitCreatedBy(String patientReasonVisitCreatedBy) {
        this.patientReasonVisitCreatedBy = patientReasonVisitCreatedBy;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public PatientRegistration getPatient() {
        return patient;
    }

    public void setPatient(PatientRegistration patient) {
        this.patient = patient;
    }
}
