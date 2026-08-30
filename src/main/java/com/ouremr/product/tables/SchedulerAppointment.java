package com.ouremr.product.tables;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "scheduler_appointment")
public class SchedulerAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduler_appointment_id")
    private Long schedulerAppointmentId;

    @Column(name = "scheduler_appointment_patientid")
    private Long schedulerAppointmentPatientId;

    @Column(name = "scheduler_appointment_patientname")
    private String schedulerAppointmentPatientName;

    @Column(name = "scheduler_appointment_appt_date")
    private LocalDate schedulerAppointmentAppointmentDate;

    @Column(name = "scheduler_appointment_reason")
    private String schedulerAppointmentReason;

    @Column(name = "scheduler_appointment_providerid")
    private Long schedulerAppointmentProviderId;

    @Column(name = "scheduler_appointment_start_time")
    private String schedulerAppointmentStartTime;

    @Column(name = "scheduler_appointment_end_time")
    private String schedulerAppointmentEndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_appointment_status")
    private SchedulerAppointmentStatus schedulerAppointmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_appointment_type")
    private SchedulerAppointmentType schedulerAppointmentType;

    @ManyToOne
    @JoinColumn(name = "scheduler_appointment_providerid", referencedColumnName = "employee_profile_id", insertable = false, updatable = false)
    private EmployeeProfile schedulerAppointmentProvider;

    @Column(name = "scheduler_appointment_created_by")
    private String schedulerAppointmentCreatedBy;

    @Column(name = "scheduler_appointment_modified_by")
    private String schedulerAppointmentModifiedBy;

    @Column(name = "scheduler_appointment_modified_on")
    private LocalDateTime schedulerAppointmentModifiedOn;

    public Long getSchedulerAppointmentId() {
        return schedulerAppointmentId;
    }

    public void setSchedulerAppointmentId(Long schedulerAppointmentId) {
        this.schedulerAppointmentId = schedulerAppointmentId;
    }

    public Long getSchedulerAppointmentPatientId() {
        return schedulerAppointmentPatientId;
    }

    public String getSchedulerAppointmentStartTime() {
        return schedulerAppointmentStartTime;
    }

    public void setSchedulerAppointmentStartTime(String schedulerAppointmentStartTime) {
        this.schedulerAppointmentStartTime = schedulerAppointmentStartTime;
    }

    public String getSchedulerAppointmentEndTime() {
        return schedulerAppointmentEndTime;
    }

    public void setSchedulerAppointmentEndTime(String schedulerAppointmentEndTime) {
        this.schedulerAppointmentEndTime = schedulerAppointmentEndTime;
    }

    public void setSchedulerAppointmentPatientId(Long schedulerAppointmentPatientId) {
        this.schedulerAppointmentPatientId = schedulerAppointmentPatientId;
    }

    public String getSchedulerAppointmentPatientName() {
        return schedulerAppointmentPatientName;
    }

    public void setSchedulerAppointmentPatientName(String schedulerAppointmentPatientName) {
        this.schedulerAppointmentPatientName = schedulerAppointmentPatientName;
    }

    public LocalDate getSchedulerAppointmentAppointmentDate() {
        return schedulerAppointmentAppointmentDate;
    }

    public void setSchedulerAppointmentAppointmentDate(LocalDate schedulerAppointmentAppointmentDate) {
        this.schedulerAppointmentAppointmentDate = schedulerAppointmentAppointmentDate;
    }

    public String getSchedulerAppointmentReason() {
        return schedulerAppointmentReason;
    }

    public void setSchedulerAppointmentReason(String schedulerAppointmentReason) {
        this.schedulerAppointmentReason = schedulerAppointmentReason;
    }

    public Long getSchedulerAppointmentProviderId() {
        return schedulerAppointmentProviderId;
    }

    public void setSchedulerAppointmentProviderId(Long schedulerAppointmentProviderId) {
        this.schedulerAppointmentProviderId = schedulerAppointmentProviderId;
    }

    public SchedulerAppointmentStatus getSchedulerAppointmentStatus() {
        return schedulerAppointmentStatus;
    }

    public void setSchedulerAppointmentStatus(SchedulerAppointmentStatus schedulerAppointmentStatus) {
        this.schedulerAppointmentStatus = schedulerAppointmentStatus;
    }

    public SchedulerAppointmentType getSchedulerAppointmentType() {
        return schedulerAppointmentType;
    }

    public void setSchedulerAppointmentType(SchedulerAppointmentType schedulerAppointmentType) {
        this.schedulerAppointmentType = schedulerAppointmentType;
    }

    public EmployeeProfile getSchedulerAppointmentProvider() {
        return schedulerAppointmentProvider;
    }

    public void setSchedulerAppointmentProvider(EmployeeProfile schedulerAppointmentProvider) {
        this.schedulerAppointmentProvider = schedulerAppointmentProvider;
    }

    public String getSchedulerAppointmentCreatedBy() {
        return schedulerAppointmentCreatedBy;
    }

    public void setSchedulerAppointmentCreatedBy(String schedulerAppointmentCreatedBy) {
        this.schedulerAppointmentCreatedBy = schedulerAppointmentCreatedBy;
    }

    public String getSchedulerAppointmentModifiedBy() {
        return schedulerAppointmentModifiedBy;
    }

    public void setSchedulerAppointmentModifiedBy(String schedulerAppointmentModifiedBy) {
        this.schedulerAppointmentModifiedBy = schedulerAppointmentModifiedBy;
    }

    public LocalDateTime getSchedulerAppointmentModifiedOn() {
        return schedulerAppointmentModifiedOn;
    }

    public void setSchedulerAppointmentModifiedOn(LocalDateTime schedulerAppointmentModifiedOn) {
        this.schedulerAppointmentModifiedOn = schedulerAppointmentModifiedOn;
    }
}