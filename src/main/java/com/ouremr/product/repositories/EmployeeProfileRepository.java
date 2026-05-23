package com.ouremr.product.repositories;

import com.ouremr.product.tables.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long>, JpaSpecificationExecutor<EmployeeProfile> {
}
