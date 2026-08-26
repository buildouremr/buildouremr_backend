package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_visit_chart")
public class PatientVisitChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_visit_chart_id")
    private Long patientVisitChartId;

    @Column(name = "patient_visit_chart_patient_id")
    private Long patientVisitChartPatientId;

    @Column(name = "patient_visit_chart_appointment_id")
    private Long patientVisitChartAppointmentId;

    @Column(name = "encounter_id")
    private Long encounterId;

    @Column(name = "patient_visit_chart_reason_for_visit", columnDefinition = "TEXT")
    private String patientVisitChartReasonForVisit;

    @Column(name = "patient_visit_chart_symptoms", columnDefinition = "TEXT")
    private String patientVisitChartSymptoms;

    @Column(name = "patient_visit_chart_examination", columnDefinition = "TEXT")
    private String patientVisitChartExamination;

    @Column(name = "patient_visit_chart_diagnosis_tests", columnDefinition = "TEXT")
    private String patientVisitChartDiagnosisTests;

    @Column(name = "patient_visit_chart_diagnosis_notes", columnDefinition = "TEXT")
    private String patientVisitChartDiagnosisNotes;

    @Column(name = "patient_visit_chart_treatment_plan", columnDefinition = "TEXT")
    private String patientVisitChartTreatmentPlan;

    @Column(name = "patient_visit_chart_advice", columnDefinition = "TEXT")
    private String patientVisitChartAdvice;

    @Column(name = "patient_visit_chart_status")
    private String patientVisitChartStatus;

    @Column(name = "patient_visit_chart_created_on")
    private LocalDateTime patientVisitChartCreatedOn;

    @Column(name = "patient_visit_chart_created_by")
    private String patientVisitChartCreatedBy;

    @Column(name = "patient_visit_chart_modified_on")
    private LocalDateTime patientVisitChartModifiedOn;

    @Column(name = "patient_visit_chart_modified_by")
    private String patientVisitChartModifiedBy;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_visit_chart_patient_id", referencedColumnName = "patient_registration_id", insertable = false, updatable = false)
    private PatientRegistration patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_visit_chart_appointment_id", referencedColumnName = "scheduler_appointment_id", insertable = false, updatable = false)
    private SchedulerAppointment appointment;

    // Getters and Setters

    public Long getPatientVisitChartId() {
        return patientVisitChartId;
    }

    public void setPatientVisitChartId(Long patientVisitChartId) {
        this.patientVisitChartId = patientVisitChartId;
    }

    public Long getPatientVisitChartPatientId() {
        return patientVisitChartPatientId;
    }

    public void setPatientVisitChartPatientId(Long patientVisitChartPatientId) {
        this.patientVisitChartPatientId = patientVisitChartPatientId;
    }

    public Long getPatientVisitChartAppointmentId() {
        return patientVisitChartAppointmentId;
    }

    public void setPatientVisitChartAppointmentId(Long patientVisitChartAppointmentId) {
        this.patientVisitChartAppointmentId = patientVisitChartAppointmentId;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public String getPatientVisitChartReasonForVisit() {
        return patientVisitChartReasonForVisit;
    }

    public void setPatientVisitChartReasonForVisit(String patientVisitChartReasonForVisit) {
        this.patientVisitChartReasonForVisit = patientVisitChartReasonForVisit;
    }

    public String getPatientVisitChartSymptoms() {
        return patientVisitChartSymptoms;
    }

    public void setPatientVisitChartSymptoms(String patientVisitChartSymptoms) {
        this.patientVisitChartSymptoms = patientVisitChartSymptoms;
    }

    public String getPatientVisitChartExamination() {
        return patientVisitChartExamination;
    }

    public void setPatientVisitChartExamination(String patientVisitChartExamination) {
        this.patientVisitChartExamination = patientVisitChartExamination;
    }

    public String getPatientVisitChartDiagnosisTests() {
        return patientVisitChartDiagnosisTests;
    }

    public void setPatientVisitChartDiagnosisTests(String patientVisitChartDiagnosisTests) {
        this.patientVisitChartDiagnosisTests = patientVisitChartDiagnosisTests;
    }

    public String getPatientVisitChartDiagnosisNotes() {
        return patientVisitChartDiagnosisNotes;
    }

    public void setPatientVisitChartDiagnosisNotes(String patientVisitChartDiagnosisNotes) {
        this.patientVisitChartDiagnosisNotes = patientVisitChartDiagnosisNotes;
    }

    public String getPatientVisitChartTreatmentPlan() {
        return patientVisitChartTreatmentPlan;
    }

    public void setPatientVisitChartTreatmentPlan(String patientVisitChartTreatmentPlan) {
        this.patientVisitChartTreatmentPlan = patientVisitChartTreatmentPlan;
    }

    public String getPatientVisitChartAdvice() {
        return patientVisitChartAdvice;
    }

    public void setPatientVisitChartAdvice(String patientVisitChartAdvice) {
        this.patientVisitChartAdvice = patientVisitChartAdvice;
    }

    public String getPatientVisitChartStatus() {
        return patientVisitChartStatus;
    }

    public void setPatientVisitChartStatus(String patientVisitChartStatus) {
        this.patientVisitChartStatus = patientVisitChartStatus;
    }

    public LocalDateTime getPatientVisitChartCreatedOn() {
        return patientVisitChartCreatedOn;
    }

    public void setPatientVisitChartCreatedOn(LocalDateTime patientVisitChartCreatedOn) {
        this.patientVisitChartCreatedOn = patientVisitChartCreatedOn;
    }

    public String getPatientVisitChartCreatedBy() {
        return patientVisitChartCreatedBy;
    }

    public void setPatientVisitChartCreatedBy(String patientVisitChartCreatedBy) {
        this.patientVisitChartCreatedBy = patientVisitChartCreatedBy;
    }

    public LocalDateTime getPatientVisitChartModifiedOn() {
        return patientVisitChartModifiedOn;
    }

    public void setPatientVisitChartModifiedOn(LocalDateTime patientVisitChartModifiedOn) {
        this.patientVisitChartModifiedOn = patientVisitChartModifiedOn;
    }

    public String getPatientVisitChartModifiedBy() {
        return patientVisitChartModifiedBy;
    }

    public void setPatientVisitChartModifiedBy(String patientVisitChartModifiedBy) {
        this.patientVisitChartModifiedBy = patientVisitChartModifiedBy;
    }

    public PatientRegistration getPatient() {
        return patient;
    }

    public void setPatient(PatientRegistration patient) {
        this.patient = patient;
    }

    public SchedulerAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(SchedulerAppointment appointment) {
        this.appointment = appointment;
    }
}
