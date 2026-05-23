package com.ouremr.product.repositories;

import com.ouremr.product.tables.SchedulerAppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulerAppointmentStatusRepository extends JpaRepository<SchedulerAppointmentStatus, Integer>, JpaSpecificationExecutor<SchedulerAppointmentStatus> {
}
