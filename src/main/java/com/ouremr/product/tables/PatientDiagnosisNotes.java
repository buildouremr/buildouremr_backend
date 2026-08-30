package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_diagnosis_notes")
public class PatientDiagnosisNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_diagnosis_notes_id")
    private Long patientDiagnosisNotesId;

    @Column(name = "patient_diagnosis_notes_data", columnDefinition = "TEXT")
    private String patientDiagnosisNotesData;

    @Column(name = "patient_diagnosis_notes_created_on")
    private LocalDateTime patientDiagnosisNotesCreatedOn;

    @Column(name = "patient_diagnosis_notes_modified_on")
    private LocalDateTime patientDiagnosisNotesModifiedOn;

    @Column(name = "is_active", insertable = false)
    private Boolean isActive = true;

    @Column(name = "patient_diagnosis_notes_created_by")
    private String patientDiagnosisNotesCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_diagnosis_notes_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_diagnosis_notes_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    public Long getPatientDiagnosisNotesId() {
        return patientDiagnosisNotesId;
    }

    public void setPatientDiagnosisNotesId(Long patientDiagnosisNotesId) {
        this.patientDiagnosisNotesId = patientDiagnosisNotesId;
    }

    public String getPatientDiagnosisNotesData() {
        return patientDiagnosisNotesData;
    }

    public void setPatientDiagnosisNotesData(String patientDiagnosisNotesData) {
        this.patientDiagnosisNotesData = patientDiagnosisNotesData;
    }

    public LocalDateTime getPatientDiagnosisNotesCreatedOn() {
        return patientDiagnosisNotesCreatedOn;
    }

    public void setPatientDiagnosisNotesCreatedOn(LocalDateTime patientDiagnosisNotesCreatedOn) {
        this.patientDiagnosisNotesCreatedOn = patientDiagnosisNotesCreatedOn;
    }

    public LocalDateTime getPatientDiagnosisNotesModifiedOn() {
        return patientDiagnosisNotesModifiedOn;
    }

    public void setPatientDiagnosisNotesModifiedOn(LocalDateTime patientDiagnosisNotesModifiedOn) {
        this.patientDiagnosisNotesModifiedOn = patientDiagnosisNotesModifiedOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getPatientDiagnosisNotesCreatedBy() {
        return patientDiagnosisNotesCreatedBy;
    }

    public void setPatientDiagnosisNotesCreatedBy(String patientDiagnosisNotesCreatedBy) {
        this.patientDiagnosisNotesCreatedBy = patientDiagnosisNotesCreatedBy;
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
