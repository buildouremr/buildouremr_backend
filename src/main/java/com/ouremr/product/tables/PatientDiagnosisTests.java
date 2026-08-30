package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_diagnosis_tests")
public class PatientDiagnosisTests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_diagnosis_tests_id")
    private Long patientDiagnosisTestsId;

    @Column(name = "patient_diagnosis_tests_data", columnDefinition = "TEXT")
    private String patientDiagnosisTestsData;

    @Column(name = "patient_diagnosis_tests_created_on")
    private LocalDateTime patientDiagnosisTestsCreatedOn;

    @Column(name = "patient_diagnosis_tests_modified_on")
    private LocalDateTime patientDiagnosisTestsModifiedOn;

    @Column(name = "patient_diagnosis_tests_created_by")
    private String patientDiagnosisTestsCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_diagnosis_tests_encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_diagnosis_tests_patient_id", referencedColumnName = "patient_registration_id", nullable = false)
    private PatientRegistration patient;

    public Long getPatientDiagnosisTestsId() {
        return patientDiagnosisTestsId;
    }

    public void setPatientDiagnosisTestsId(Long patientDiagnosisTestsId) {
        this.patientDiagnosisTestsId = patientDiagnosisTestsId;
    }

    public String getPatientDiagnosisTestsData() {
        return patientDiagnosisTestsData;
    }

    public void setPatientDiagnosisTestsData(String patientDiagnosisTestsData) {
        this.patientDiagnosisTestsData = patientDiagnosisTestsData;
    }

    public LocalDateTime getPatientDiagnosisTestsCreatedOn() {
        return patientDiagnosisTestsCreatedOn;
    }

    public void setPatientDiagnosisTestsCreatedOn(LocalDateTime patientDiagnosisTestsCreatedOn) {
        this.patientDiagnosisTestsCreatedOn = patientDiagnosisTestsCreatedOn;
    }

    public LocalDateTime getPatientDiagnosisTestsModifiedOn() {
        return patientDiagnosisTestsModifiedOn;
    }

    public void setPatientDiagnosisTestsModifiedOn(LocalDateTime patientDiagnosisTestsModifiedOn) {
        this.patientDiagnosisTestsModifiedOn = patientDiagnosisTestsModifiedOn;
    }

    public String getPatientDiagnosisTestsCreatedBy() {
        return patientDiagnosisTestsCreatedBy;
    }

    public void setPatientDiagnosisTestsCreatedBy(String patientDiagnosisTestsCreatedBy) {
        this.patientDiagnosisTestsCreatedBy = patientDiagnosisTestsCreatedBy;
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
