package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_examination")
public class PatientExamination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_examination_id")
    private Long patientExaminationId;

    @Column(name = "patient_examination_data", columnDefinition = "TEXT")
    private String patientExaminationData;

    @Column(name = "patient_examination_created_on")
    private LocalDateTime patientExaminationCreatedOn;

    @Column(name = "patient_examination_modified_on")
    private LocalDateTime patientExaminationModifiedOn;

    @Column(name = "patient_examination_created_by")
    private String patientExaminationCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_examination_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_examination_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    // Getters and Setters
    public Long getPatientExaminationId() {
        return patientExaminationId;
    }

    public void setPatientExaminationId(Long patientExaminationId) {
        this.patientExaminationId = patientExaminationId;
    }

    public String getPatientExaminationData() {
        return patientExaminationData;
    }

    public void setPatientExaminationData(String patientExaminationData) {
        this.patientExaminationData = patientExaminationData;
    }

    public LocalDateTime getPatientExaminationCreatedOn() {
        return patientExaminationCreatedOn;
    }

    public void setPatientExaminationCreatedOn(LocalDateTime patientExaminationCreatedOn) {
        this.patientExaminationCreatedOn = patientExaminationCreatedOn;
    }

    public LocalDateTime getPatientExaminationModifiedOn() {
        return patientExaminationModifiedOn;
    }

    public void setPatientExaminationModifiedOn(LocalDateTime patientExaminationModifiedOn) {
        this.patientExaminationModifiedOn = patientExaminationModifiedOn;
    }

    public String getPatientExaminationCreatedBy() {
        return patientExaminationCreatedBy;
    }

    public void setPatientExaminationCreatedBy(String patientExaminationCreatedBy) {
        this.patientExaminationCreatedBy = patientExaminationCreatedBy;
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
