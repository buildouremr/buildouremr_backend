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

    @Column(name = "patient_visit_chart_id")
    private Long patientVisitChartId;

    @Column(name = "encounter_id")
    private Long encounterId;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "vital_header")
    private String vitalHeader;

    @Column(name = "vital_data")
    private String vitalData;

    @Column(name = "patient_vitals_created_on")
    private LocalDateTime patientVitalsCreatedOn;

    @Column(name = "patient_vitals_created_by")
    private String patientVitalsCreatedBy;

    @Column(name = "patient_vitals_modified_on")
    private LocalDateTime patientVitalsModifiedOn;

    @Column(name = "patient_vitals_modified_by")
    private String patientVitalsModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_visit_chart_id", referencedColumnName = "patient_visit_chart_id", insertable = false, updatable = false)
    private PatientVisitChart patientVisitChart;

    public Long getPatientVitalsId() {
        return patientVitalsId;
    }

    public void setPatientVitalsId(Long patientVitalsId) {
        this.patientVitalsId = patientVitalsId;
    }

    public Long getPatientVisitChartId() {
        return patientVisitChartId;
    }

    public void setPatientVisitChartId(Long patientVisitChartId) {
        this.patientVisitChartId = patientVisitChartId;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getVitalHeader() {
        return vitalHeader;
    }

    public void setVitalHeader(String vitalHeader) {
        this.vitalHeader = vitalHeader;
    }

    public String getVitalData() {
        return vitalData;
    }

    public void setVitalData(String vitalData) {
        this.vitalData = vitalData;
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

    public PatientVisitChart getPatientVisitChart() {
        return patientVisitChart;
    }

    public void setPatientVisitChart(PatientVisitChart patientVisitChart) {
        this.patientVisitChart = patientVisitChart;
    }
}
