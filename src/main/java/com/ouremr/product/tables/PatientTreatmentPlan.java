package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_treatment_plan")
public class PatientTreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_treatment_plan_id")
    private Long patientTreatmentPlanId;

    @Column(name = "patient_treatment_plan_data", columnDefinition = "TEXT")
    private String patientTreatmentPlanData;

    @Column(name = "patient_treatment_plan_created_on")
    private LocalDateTime patientTreatmentPlanCreatedOn;

    @Column(name = "patient_treatment_plan_modified_on")
    private LocalDateTime patientTreatmentPlanModifiedOn;

    @Column(name = "patient_treatment_plan_created_by")
    private String patientTreatmentPlanCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_treatment_plan_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_treatment_plan_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    public Long getPatientTreatmentPlanId() {
        return patientTreatmentPlanId;
    }

    public void setPatientTreatmentPlanId(Long patientTreatmentPlanId) {
        this.patientTreatmentPlanId = patientTreatmentPlanId;
    }

    public String getPatientTreatmentPlanData() {
        return patientTreatmentPlanData;
    }

    public void setPatientTreatmentPlanData(String patientTreatmentPlanData) {
        this.patientTreatmentPlanData = patientTreatmentPlanData;
    }

    public LocalDateTime getPatientTreatmentPlanCreatedOn() {
        return patientTreatmentPlanCreatedOn;
    }

    public void setPatientTreatmentPlanCreatedOn(LocalDateTime patientTreatmentPlanCreatedOn) {
        this.patientTreatmentPlanCreatedOn = patientTreatmentPlanCreatedOn;
    }

    public LocalDateTime getPatientTreatmentPlanModifiedOn() {
        return patientTreatmentPlanModifiedOn;
    }

    public void setPatientTreatmentPlanModifiedOn(LocalDateTime patientTreatmentPlanModifiedOn) {
        this.patientTreatmentPlanModifiedOn = patientTreatmentPlanModifiedOn;
    }

    public String getPatientTreatmentPlanCreatedBy() {
        return patientTreatmentPlanCreatedBy;
    }

    public void setPatientTreatmentPlanCreatedBy(String patientTreatmentPlanCreatedBy) {
        this.patientTreatmentPlanCreatedBy = patientTreatmentPlanCreatedBy;
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
