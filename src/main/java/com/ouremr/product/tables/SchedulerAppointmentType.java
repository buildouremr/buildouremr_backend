package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "scheduler_appointment_types")
public class SchedulerAppointmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scheduler_appointment_type_id")
    private Long schedulerAppointmentTypeId;

    @Column(name = "scheduler_appointment_type_name")
    private String schedulerAppointmentTypeName;

    @Column(name = "scheduler_appointment_isactive")
    private Boolean schedulerAppointmentTypeIsActive;

    public Long getSchedulerAppointmentTypeId() {
        return schedulerAppointmentTypeId;
    }

    public void setSchedulerAppointmentTypeId(Long schedulerAppointmentTypeId) {
        this.schedulerAppointmentTypeId = schedulerAppointmentTypeId;
    }

    public String getSchedulerAppointmentTypeName() {
        return schedulerAppointmentTypeName;
    }

    public void setSchedulerAppointmentTypeName(String schedulerAppointmentTypeName) {
        this.schedulerAppointmentTypeName = schedulerAppointmentTypeName;
    }

    public Boolean getSchedulerAppointmentTypeIsActive() {
        return schedulerAppointmentTypeIsActive;
    }

    public void setSchedulerAppointmentTypeIsActive(Boolean schedulerAppointmentTypeIsActive) {
        this.schedulerAppointmentTypeIsActive = schedulerAppointmentTypeIsActive;
    }
}