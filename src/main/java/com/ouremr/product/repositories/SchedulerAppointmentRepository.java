package com.ouremr.product.repositories;

import com.ouremr.product.tables.SchedulerAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulerAppointmentRepository extends JpaRepository<SchedulerAppointment, Long>, JpaSpecificationExecutor<SchedulerAppointment> {
}
