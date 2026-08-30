package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patient_symptoms")
public class PatientSymptoms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_symptoms_id")
    private Long patientSymptomsId;

    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "patient_symptoms_data", columnDefinition = "jsonb")
    private List<String> patientSymptomsData;

    @Column(name = "patient_symptoms_created_on")
    private LocalDateTime patientSymptomsCreatedOn;

    @Column(name = "patient_symptoms_modified_on")
    private LocalDateTime patientSymptomsModifiedOn;

    @Column(name = "patient_symptoms_created_by")
    private String patientSymptomsCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_symptoms_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_symptoms_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    // Getters and Setters
    public Long getPatientSymptomsId() {
        return patientSymptomsId;
    }

    public void setPatientSymptomsId(Long patientSymptomsId) {
        this.patientSymptomsId = patientSymptomsId;
    }

    public List<String> getPatientSymptomsData() {
        return patientSymptomsData;
    }

    public void setPatientSymptomsData(List<String> patientSymptomsData) {
        this.patientSymptomsData = patientSymptomsData;
    }

    public LocalDateTime getPatientSymptomsCreatedOn() {
        return patientSymptomsCreatedOn;
    }

    public void setPatientSymptomsCreatedOn(LocalDateTime patientSymptomsCreatedOn) {
        this.patientSymptomsCreatedOn = patientSymptomsCreatedOn;
    }

    public LocalDateTime getPatientSymptomsModifiedOn() {
        return patientSymptomsModifiedOn;
    }

    public void setPatientSymptomsModifiedOn(LocalDateTime patientSymptomsModifiedOn) {
        this.patientSymptomsModifiedOn = patientSymptomsModifiedOn;
    }

    public String getPatientSymptomsCreatedBy() {
        return patientSymptomsCreatedBy;
    }

    public void setPatientSymptomsCreatedBy(String patientSymptomsCreatedBy) {
        this.patientSymptomsCreatedBy = patientSymptomsCreatedBy;
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
