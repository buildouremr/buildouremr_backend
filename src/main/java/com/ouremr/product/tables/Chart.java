package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chart")
public class Chart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chart_id")
    private Long chartId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_registration_id", insertable = false, updatable = false)
    private PatientRegistration patient;

    // Getters and Setters

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public PatientRegistration getPatient() {
        return patient;
    }

    public void setPatient(PatientRegistration patient) {
        this.patient = patient;
    }
}
