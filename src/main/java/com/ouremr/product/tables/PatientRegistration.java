package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_registration")
public class PatientRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_registration_id")
    private Long patientRegistrationId;

    @Column(name = "patient_registration_firstname")
    private String patientRegistrationFirstName;

    @Column(name = "patient_registration_lastname")
    private String patientRegistrationLastName;

    @Column(name = "patient_registration_middlename")
    private String patientRegistrationMiddleName;

    @Column(name = "patient_registration_gaurdianname")
    private String patientRegistrationGuardianName;

    @Column(name = "patient_registration_dob")
    private LocalDate patientRegistrationDob;

    @Column(name = "patient_registration_sex")
    private String patientRegistrationSex;

    @Column(name = "patient_registration_address")
    private String patientRegistrationAddress;

    @Column(name = "patient_registration_state")
    private String patientRegistrationState;

    @Column(name = "patient_registration_city")
    private String patientRegistrationCity;

    @Column(name = "patient_registration_pincode")
    private String patientRegistrationPincode;

    @Column(name = "patient_registration_insurancename")
    private String patientRegistrationInsuranceName;

    @Column(name = "patient_registration_chronic")
    private String patientRegistrationChronic;

    @Column(name = "patient_registration_call_reminder")
    private Boolean patientRegistrationCallReminder;

    @Column(name = "patient_registration_text_reminder")
    private Boolean patientRegistrationTextReminder;

    @Column(name = "patient_registration_mobile_no")
    private String patientRegistrationMobileNo;

    @Column(name = "patient_registration_other_mobile_no")
    private String patientRegistrationOtherMobileNo;

    @Column(name = "patient_registration_image")
    private String patientRegistrationImage;


    @Column(name = "patient_registration_email_id")
    private String patientRegistrationEmailId;

    @Column(name = "patient_registration_active")
    private Boolean patientRegistrationActive;

    @Column(name = "patient_registration_principal_doctor")
    private Long patientRegistrationPrincipalDoctor;



    @Column(name = "patient_registration_allergies")
    private String patientRegistrationAllergies;

    @Column(name = "patient_registration_risk_factors")
    private String patientRegistrationRiskFactors;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    public Long getPatientRegistrationId() {
        return patientRegistrationId;
    }

    public void setPatientRegistrationId(Long patientRegistrationId) {
        this.patientRegistrationId = patientRegistrationId;
    }

    public String getPatientRegistrationFirstName() {
        return patientRegistrationFirstName;
    }

    public void setPatientRegistrationFirstName(String patientRegistrationFirstName) {
        this.patientRegistrationFirstName = patientRegistrationFirstName;
    }

    public String getPatientRegistrationLastName() {
        return patientRegistrationLastName;
    }

    public void setPatientRegistrationLastName(String patientRegistrationLastName) {
        this.patientRegistrationLastName = patientRegistrationLastName;
    }

    public String getPatientRegistrationMiddleName() {
        return patientRegistrationMiddleName;
    }

    public void setPatientRegistrationMiddleName(String patientRegistrationMiddleName) {
        this.patientRegistrationMiddleName = patientRegistrationMiddleName;
    }

    public String getPatientRegistrationGuardianName() {
        return patientRegistrationGuardianName;
    }

    public void setPatientRegistrationGuardianName(String patientRegistrationGuardianName) {
        this.patientRegistrationGuardianName = patientRegistrationGuardianName;
    }

    public LocalDate getPatientRegistrationDob() {
        return patientRegistrationDob;
    }

    public void setPatientRegistrationDob(LocalDate patientRegistrationDob) {
        this.patientRegistrationDob = patientRegistrationDob;
    }

    public String getPatientRegistrationSex() {
        return patientRegistrationSex;
    }

    public void setPatientRegistrationSex(String patientRegistrationSex) {
        this.patientRegistrationSex = patientRegistrationSex;
    }

    public String getPatientRegistrationAddress() {
        return patientRegistrationAddress;
    }

    public void setPatientRegistrationAddress(String patientRegistrationAddress) {
        this.patientRegistrationAddress = patientRegistrationAddress;
    }

    public String getPatientRegistrationState() {
        return patientRegistrationState;
    }

    public void setPatientRegistrationState(String patientRegistrationState) {
        this.patientRegistrationState = patientRegistrationState;
    }

    public String getPatientRegistrationCity() {
        return patientRegistrationCity;
    }

    public void setPatientRegistrationCity(String patientRegistrationCity) {
        this.patientRegistrationCity = patientRegistrationCity;
    }

    public String getPatientRegistrationPincode() {
        return patientRegistrationPincode;
    }

    public void setPatientRegistrationPincode(String patientRegistrationPincode) {
        this.patientRegistrationPincode = patientRegistrationPincode;
    }

    public String getPatientRegistrationInsuranceName() {
        return patientRegistrationInsuranceName;
    }

    public void setPatientRegistrationInsuranceName(String patientRegistrationInsuranceName) {
        this.patientRegistrationInsuranceName = patientRegistrationInsuranceName;
    }

    public String getPatientRegistrationChronic() {
        return patientRegistrationChronic;
    }

    public void setPatientRegistrationChronic(String patientRegistrationChronic) {
        this.patientRegistrationChronic = patientRegistrationChronic;
    }

    public Boolean getPatientRegistrationCallReminder() {
        return patientRegistrationCallReminder;
    }

    public void setPatientRegistrationCallReminder(Boolean patientRegistrationCallReminder) {
        this.patientRegistrationCallReminder = patientRegistrationCallReminder;
    }

    public Boolean getPatientRegistrationTextReminder() {
        return patientRegistrationTextReminder;
    }

    public void setPatientRegistrationTextReminder(Boolean patientRegistrationTextReminder) {
        this.patientRegistrationTextReminder = patientRegistrationTextReminder;
    }

    public String getPatientRegistrationMobileNo() {
        return patientRegistrationMobileNo;
    }

    public void setPatientRegistrationMobileNo(String patientRegistrationMobileNo) {
        this.patientRegistrationMobileNo = patientRegistrationMobileNo;
    }

    public String getPatientRegistrationOtherMobileNo() {
        return patientRegistrationOtherMobileNo;
    }

    public void setPatientRegistrationOtherMobileNo(String patientRegistrationOtherMobileNo) {
        this.patientRegistrationOtherMobileNo = patientRegistrationOtherMobileNo;
    }

    public String getPatientRegistrationImage() {
        return patientRegistrationImage;
    }

    public void setPatientRegistrationImage(String patientRegistrationImage) {
        this.patientRegistrationImage = patientRegistrationImage;
    }


    public String getPatientRegistrationEmailId() {
        return patientRegistrationEmailId;
    }

    public void setPatientRegistrationEmailId(String patientRegistrationEmailId) {
        this.patientRegistrationEmailId = patientRegistrationEmailId;
    }

    public Boolean getPatientRegistrationActive() {
        return patientRegistrationActive;
    }

    public void setPatientRegistrationActive(Boolean patientRegistrationActive) {
        this.patientRegistrationActive = patientRegistrationActive;
    }

    public Long getPatientRegistrationPrincipalDoctor() {
        return patientRegistrationPrincipalDoctor;
    }

    public void setPatientRegistrationPrincipalDoctor(Long patientRegistrationPrincipalDoctor) {
        this.patientRegistrationPrincipalDoctor = patientRegistrationPrincipalDoctor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_registration_principal_doctor", referencedColumnName = "employee_profile_id", insertable = false, updatable = false)
    private EmployeeProfile principalDoctor;

    public EmployeeProfile getPrincipalDoctor() {
        return principalDoctor;
    }

    public void setPrincipalDoctor(EmployeeProfile principalDoctor) {
        this.principalDoctor = principalDoctor;
    }


    public String getPatientRegistrationAllergies() {
        return patientRegistrationAllergies;
    }

    public void setPatientRegistrationAllergies(String patientRegistrationAllergies) {
        this.patientRegistrationAllergies = patientRegistrationAllergies;
    }

    public String getPatientRegistrationRiskFactors() {
        return patientRegistrationRiskFactors;
    }

    public void setPatientRegistrationRiskFactors(String patientRegistrationRiskFactors) {
        this.patientRegistrationRiskFactors = patientRegistrationRiskFactors;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

}