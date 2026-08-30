package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_advice")
public class PatientAdvice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_advice_id")
    private Long patientAdviceId;

    @Column(name = "patient_advice_data", columnDefinition = "TEXT")
    private String patientAdviceData;

    @Column(name = "patient_advice_created_on")
    private LocalDateTime patientAdviceCreatedOn;

    @Column(name = "patient_advice_modified_on")
    private LocalDateTime patientAdviceModifiedOn;

    @Column(name = "patient_advice_created_by")
    private String patientAdviceCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_advice_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_advice_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    public Long getPatientAdviceId() {
        return patientAdviceId;
    }

    public void setPatientAdviceId(Long patientAdviceId) {
        this.patientAdviceId = patientAdviceId;
    }

    public String getPatientAdviceData() {
        return patientAdviceData;
    }

    public void setPatientAdviceData(String patientAdviceData) {
        this.patientAdviceData = patientAdviceData;
    }

    public LocalDateTime getPatientAdviceCreatedOn() {
        return patientAdviceCreatedOn;
    }

    public void setPatientAdviceCreatedOn(LocalDateTime patientAdviceCreatedOn) {
        this.patientAdviceCreatedOn = patientAdviceCreatedOn;
    }

    public LocalDateTime getPatientAdviceModifiedOn() {
        return patientAdviceModifiedOn;
    }

    public void setPatientAdviceModifiedOn(LocalDateTime patientAdviceModifiedOn) {
        this.patientAdviceModifiedOn = patientAdviceModifiedOn;
    }

    public String getPatientAdviceCreatedBy() {
        return patientAdviceCreatedBy;
    }

    public void setPatientAdviceCreatedBy(String patientAdviceCreatedBy) {
        this.patientAdviceCreatedBy = patientAdviceCreatedBy;
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
