package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_profile_roles")
public class EmployeeProfileRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_profile_role_id")
    private Long employeeProfileRoleId;

    @Column(name = "employee_profile_role_name")
    private String employeeProfileRoleName;

    @Column(name = "employee_profile_role_isactive")
    private Boolean employeeProfileIsActive;

    public Long getEmployeeProfileRoleId() {
        return employeeProfileRoleId;
    }

    public void setEmployeeProfileRoleId(Long employeeProfileRoleId) {
        this.employeeProfileRoleId = employeeProfileRoleId;
    }

    public String getEmployeeProfileRoleName() {
        return employeeProfileRoleName;
    }

    public void setEmployeeProfileRoleName(String employeeProfileRoleName) {
        this.employeeProfileRoleName = employeeProfileRoleName;
    }

    public Boolean getEmployeeProfileIsActive() {
        return employeeProfileIsActive;
    }

    public void setEmployeeProfileIsActive(Boolean employeeProfileIsActive) {
        this.employeeProfileIsActive = employeeProfileIsActive;
    }
}
