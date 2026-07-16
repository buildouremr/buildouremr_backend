package com.ouremr.product.repositories;

import com.ouremr.product.tables.SchedulerAppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulerAppointmentTypesRepository extends JpaRepository<SchedulerAppointmentType, Long>, JpaSpecificationExecutor<SchedulerAppointmentType> {
}
