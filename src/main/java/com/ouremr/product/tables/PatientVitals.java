package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_vitals")
public class PatientVitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_vitals_id")
    private Long patientVitalsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id", referencedColumnName = "encounter_id", nullable = false)
    private Encounter encounter;

    @Column(name = "patient_id")
    private Long patientId;

    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    @Column(name = "patient_vital_data", columnDefinition = "jsonb default '{\"BP\": {\"value\": \"-- / --\", \"date\": \"-\"}, \"HR\": {\"value\": \"-- bpm\", \"date\": \"-\"}, \"SpO2\": {\"value\": \"--%\", \"date\": \"-\"}, \"Temp\": {\"value\": \"-- °C\", \"date\": \"-\"}, \"Height\": {\"value\": \"-\", \"date\": \"-\"}, \"Weight\": {\"value\": \"-\", \"date\": \"-\"}, \"BMI\": {\"value\": \"-\", \"date\": \"-\"}, \"Blood Group\": {\"value\": \"-\", \"date\": \"-\"}}'")
    private java.util.Map<String, Object> patientVitalData = new java.util.HashMap<>(java.util.Map.of(
        "BP", java.util.Map.of("value", "-- / --", "date", "-"),
        "HR", java.util.Map.of("value", "-- bpm", "date", "-"),
        "SpO2", java.util.Map.of("value", "--%", "date", "-"),
        "Temp", java.util.Map.of("value", "-- °C", "date", "-"),
        "Height", java.util.Map.of("value", "-", "date", "-"),
        "Weight", java.util.Map.of("value", "-", "date", "-"),
        "BMI", java.util.Map.of("value", "-", "date", "-"),
        "Blood Group", java.util.Map.of("value", "-", "date", "-")
    ));

    @Column(name = "patient_vitals_created_on")
    private LocalDateTime patientVitalsCreatedOn;

    @Column(name = "patient_vitals_created_by")
    private String patientVitalsCreatedBy;

    @Column(name = "patient_vitals_modified_on")
    private LocalDateTime patientVitalsModifiedOn;

    @Column(name = "patient_vitals_modified_by")
    private String patientVitalsModifiedBy;


    public Long getPatientVitalsId() {
        return patientVitalsId;
    }

    public void setPatientVitalsId(Long patientVitalsId) {
        this.patientVitalsId = patientVitalsId;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public java.util.Map<String, Object> getPatientVitalData() {
        return patientVitalData;
    }

    public void setPatientVitalData(java.util.Map<String, Object> patientVitalData) {
        this.patientVitalData = patientVitalData;
    }

    public LocalDateTime getPatientVitalsCreatedOn() {
        return patientVitalsCreatedOn;
    }

    public void setPatientVitalsCreatedOn(LocalDateTime patientVitalsCreatedOn) {
        this.patientVitalsCreatedOn = patientVitalsCreatedOn;
    }

    public String getPatientVitalsCreatedBy() {
        return patientVitalsCreatedBy;
    }

    public void setPatientVitalsCreatedBy(String patientVitalsCreatedBy) {
        this.patientVitalsCreatedBy = patientVitalsCreatedBy;
    }

    public LocalDateTime getPatientVitalsModifiedOn() {
        return patientVitalsModifiedOn;
    }

    public void setPatientVitalsModifiedOn(LocalDateTime patientVitalsModifiedOn) {
        this.patientVitalsModifiedOn = patientVitalsModifiedOn;
    }

    public String getPatientVitalsModifiedBy() {
        return patientVitalsModifiedBy;
    }

    public void setPatientVitalsModifiedBy(String patientVitalsModifiedBy) {
        this.patientVitalsModifiedBy = patientVitalsModifiedBy;
    }

}
