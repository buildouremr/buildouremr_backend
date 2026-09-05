package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_chronic_conditions")
public class PatientChronicConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_chronic_conditions_id")
    private Long patientChronicConditionsId;

    @Column(name = "patient_id")
    private Long patientId;

    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "patient_chronic_conditions_data", columnDefinition = "jsonb default '{}'")
    private java.util.Map<String, Object> patientChronicConditionsData = new java.util.HashMap<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public PatientChronicConditions() {
    }

    public PatientChronicConditions(Long patientId, java.util.Map<String, Object> patientChronicConditionsData) {
        this.patientId = patientId;
        this.patientChronicConditionsData = patientChronicConditionsData;
    }

    // Getters and Setters

    public Long getPatientChronicConditionsId() {
        return patientChronicConditionsId;
    }

    public void setPatientChronicConditionsId(Long patientChronicConditionsId) {
        this.patientChronicConditionsId = patientChronicConditionsId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public java.util.Map<String, Object> getPatientChronicConditionsData() {
        return patientChronicConditionsData;
    }

    public void setPatientChronicConditionsData(java.util.Map<String, Object> patientChronicConditionsData) {
        this.patientChronicConditionsData = patientChronicConditionsData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
