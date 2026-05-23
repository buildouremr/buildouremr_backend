package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_profile")
public class EmployeeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_profile_id")
    private Long employeeProfileId;

    @Column(name = "employee_profile_firstname", nullable = false)
    private String employeeProfileFirstName;

    @Column(name = "employee_profile_lastname")
    private String employeeProfileLastName;

    @Column(name = "employee_profile_middlename")
    private String employeeProfileMiddleName;

    @Column(name = "employee_profile_dob")
    private LocalDate employeeProfileDob;

    @Column(name = "employee_profile_mobileno", unique = true)
    private String employeeProfileMobileNo;

    @Column(name = "employee_profile_other_mobileno", unique = true)
    private String employeeProfileOtherMobileNo;

    @Column(name = "employee_profile_email_id")
    private String employeeProfileEmailId;

    @Column(name = "employee_profile_address")
    private String employeeProfileAddress;

    @Column(name = "employee_profile_state")
    private String employeeProfileState;

    @Column(name = "employee_profile_city")
    private String employeeProfileCity;

    @Column(name = "employee_profile_pincode")
    private String employeeProfilePincode;

    @Column(name = "employee_profile_image")
    private String employeeProfileImage;

    @Column(name = "employee_profile_role")
    private String employeeProfileRoleName;

    @Column(name = "employee_profile_isactive")
    private Boolean employeeProfileIsActive;

    @Column(name = "employee_profile_blood_group")
    private String employeeProfileBloodGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_role_id")
    private EmployeeProfileRoles employeeProfileRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_speciality")
    private EmployeeSpeciality employeeSpeciality;

    public Long getEmployeeProfileId() {
        return employeeProfileId;
    }

    public void setEmployeeProfileId(Long employeeProfileId) {
        this.employeeProfileId = employeeProfileId;
    }

    public String getEmployeeProfileFirstName() {
        return employeeProfileFirstName;
    }

    public void setEmployeeProfileFirstName(String employeeProfileFirstName) {
        this.employeeProfileFirstName = employeeProfileFirstName;
    }

    public String getEmployeeProfileLastName() {
        return employeeProfileLastName;
    }

    public void setEmployeeProfileLastName(String employeeProfileLastName) {
        this.employeeProfileLastName = employeeProfileLastName;
    }

    public String getEmployeeProfileMiddleName() {
        return employeeProfileMiddleName;
    }

    public void setEmployeeProfileMiddleName(String employeeProfileMiddleName) {
        this.employeeProfileMiddleName = employeeProfileMiddleName;
    }

    public LocalDate getEmployeeProfileDob() {
        return employeeProfileDob;
    }

    public void setEmployeeProfileDob(LocalDate employeeProfileDob) {
        this.employeeProfileDob = employeeProfileDob;
    }

    public String getEmployeeProfileMobileNo() {
        return employeeProfileMobileNo;
    }

    public void setEmployeeProfileMobileNo(String employeeProfileMobileNo) {
        this.employeeProfileMobileNo = employeeProfileMobileNo;
    }

    public String getEmployeeProfileOtherMobileNo() {
        return employeeProfileOtherMobileNo;
    }

    public void setEmployeeProfileOtherMobileNo(String employeeProfileOtherMobileNo) {
        this.employeeProfileOtherMobileNo = employeeProfileOtherMobileNo;
    }

    public String getEmployeeProfileEmailId() {
        return employeeProfileEmailId;
    }

    public void setEmployeeProfileEmailId(String employeeProfileEmailId) {
        this.employeeProfileEmailId = employeeProfileEmailId;
    }

    public String getEmployeeProfileAddress() {
        return employeeProfileAddress;
    }

    public void setEmployeeProfileAddress(String employeeProfileAddress) {
        this.employeeProfileAddress = employeeProfileAddress;
    }

    public String getEmployeeProfileState() {
        return employeeProfileState;
    }

    public void setEmployeeProfileState(String employeeProfileState) {
        this.employeeProfileState = employeeProfileState;
    }

    public String getEmployeeProfileCity() {
        return employeeProfileCity;
    }

    public void setEmployeeProfileCity(String employeeProfileCity) {
        this.employeeProfileCity = employeeProfileCity;
    }

    public String getEmployeeProfilePincode() {
        return employeeProfilePincode;
    }

    public void setEmployeeProfilePincode(String employeeProfilePincode) {
        this.employeeProfilePincode = employeeProfilePincode;
    }

    public String getEmployeeProfileImage() {
        return employeeProfileImage;
    }

    public void setEmployeeProfileImage(String employeeProfileImage) {
        this.employeeProfileImage = employeeProfileImage;
    }

    public EmployeeProfileRoles getEmployeeProfileRoles() {
        return employeeProfileRoles;
    }

    public void setEmployeeProfileRoles(EmployeeProfileRoles employeeProfileRoles) {
        this.employeeProfileRoles = employeeProfileRoles;
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

    public String getEmployeeProfileBloodGroup() {
        return employeeProfileBloodGroup;
    }

    public void setEmployeeProfileBloodGroup(String employeeProfileBloodGroup) {
        this.employeeProfileBloodGroup = employeeProfileBloodGroup;
    }

    public EmployeeSpeciality getEmployeeSpeciality() {
        return employeeSpeciality;
    }

    public void setEmployeeSpeciality(EmployeeSpeciality employeeSpeciality) {
        this.employeeSpeciality = employeeSpeciality;
    }
}