package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "scheduler_appointment_status")
public class SchedulerAppointmentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduler_appointment_status_id")
    private Long schedulerAppointmentId;

    @Column(name = "scheduler_appointment_status_name")
    private String schedulerAppointmentName;

    @Column(name = "scheduler_appointment_isactive")
    private Boolean schedulerAppointmentIsActive;

    public Long getSchedulerAppointmentId() {
        return schedulerAppointmentId;
    }

    public void setSchedulerAppointmentId(Long schedulerAppointmentId) {
        this.schedulerAppointmentId = schedulerAppointmentId;
    }

    public String getSchedulerAppointmentName() {
        return schedulerAppointmentName;
    }

    public void setSchedulerAppointmentName(String schedulerAppointmentName) {
        this.schedulerAppointmentName = schedulerAppointmentName;
    }

    public Boolean getSchedulerAppointmentIsActive() {
        return schedulerAppointmentIsActive;
    }

    public void setSchedulerAppointmentIsActive(Boolean schedulerAppointmentIsActive) {
        this.schedulerAppointmentIsActive = schedulerAppointmentIsActive;
    }
}
