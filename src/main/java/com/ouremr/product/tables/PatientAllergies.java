package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_allergies")
public class PatientAllergies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_allergies_id")
    private Long patientAllergiesId;

    @Column(name = "patient_id")
    private Long patientId;

    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "patient_allergies_data", columnDefinition = "jsonb default '{}'")
    private java.util.Map<String, Object> patientAllergiesData = new java.util.HashMap<>();

    @Column(name = "patient_allergies_created_on")
    private LocalDateTime patientAllergiesCreatedOn;

    @Column(name = "patient_allergies_created_by")
    private String patientAllergiesCreatedBy;

    @Column(name = "patient_allergies_modified_on")
    private LocalDateTime patientAllergiesModifiedOn;

    @Column(name = "patient_allergies_modified_by")
    private String patientAllergiesModifiedBy;

    public Long getPatientAllergiesId() {
        return patientAllergiesId;
    }

    public void setPatientAllergiesId(Long patientAllergiesId) {
        this.patientAllergiesId = patientAllergiesId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public java.util.Map<String, Object> getPatientAllergiesData() {
        return patientAllergiesData;
    }

    public void setPatientAllergiesData(java.util.Map<String, Object> patientAllergiesData) {
        this.patientAllergiesData = patientAllergiesData;
    }

    public LocalDateTime getPatientAllergiesCreatedOn() {
        return patientAllergiesCreatedOn;
    }

    public void setPatientAllergiesCreatedOn(LocalDateTime patientAllergiesCreatedOn) {
        this.patientAllergiesCreatedOn = patientAllergiesCreatedOn;
    }

    public String getPatientAllergiesCreatedBy() {
        return patientAllergiesCreatedBy;
    }

    public void setPatientAllergiesCreatedBy(String patientAllergiesCreatedBy) {
        this.patientAllergiesCreatedBy = patientAllergiesCreatedBy;
    }

    public LocalDateTime getPatientAllergiesModifiedOn() {
        return patientAllergiesModifiedOn;
    }

    public void setPatientAllergiesModifiedOn(LocalDateTime patientAllergiesModifiedOn) {
        this.patientAllergiesModifiedOn = patientAllergiesModifiedOn;
    }

    public String getPatientAllergiesModifiedBy() {
        return patientAllergiesModifiedBy;
    }

    public void setPatientAllergiesModifiedBy(String patientAllergiesModifiedBy) {
        this.patientAllergiesModifiedBy = patientAllergiesModifiedBy;
    }
}
