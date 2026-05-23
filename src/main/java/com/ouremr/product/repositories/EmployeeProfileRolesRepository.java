package com.ouremr.product.repositories;

import com.ouremr.product.tables.EmployeeProfileRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeProfileRolesRepository extends JpaRepository<EmployeeProfileRoles, Integer>, JpaSpecificationExecutor<EmployeeProfileRoles> {
}
