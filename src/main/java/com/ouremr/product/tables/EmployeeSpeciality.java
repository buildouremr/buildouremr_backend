package com.ouremr.product.tables;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_speciality")
public class EmployeeSpeciality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_speciality_id")
    private Integer employeeSpecialityId;

    @Column(name = "employee_speciality_name")
    private String employeeSpecialityName;

    @Column(name = "employee_speciality_isactive")
    private Boolean employeeSpecialityIsActive;

    public Integer getEmployeeSpecialityId() {
        return employeeSpecialityId;
    }

    public void setEmployeeSpecialityId(Integer employeeSpecialityId) {
        this.employeeSpecialityId = employeeSpecialityId;
    }

    public String getEmployeeSpecialityName() {
        return employeeSpecialityName;
    }

    public void setEmployeeSpecialityName(String employeeSpecialityName) {
        this.employeeSpecialityName = employeeSpecialityName;
    }

    public Boolean getEmployeeSpecialityIsActive() {
        return employeeSpecialityIsActive;
    }

    public void setEmployeeSpecialityIsActive(Boolean employeeSpecialityIsActive) {
        this.employeeSpecialityIsActive = employeeSpecialityIsActive;
    }
}
