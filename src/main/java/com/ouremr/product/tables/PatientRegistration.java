package com.ouremr.product.tables;

import jakarta.persistence.*;
import java.time.LocalDate;

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
    private Boolean patientRegistrationChronic;

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

    @Column(name = "patient_registration_blood_group")
    private String patientRegistrationBloodGroup;

    @Column(name = "patient_registration_email_id")
    private String patientRegistrationEmailId;

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

    public Boolean getPatientRegistrationChronic() {
        return patientRegistrationChronic;
    }

    public void setPatientRegistrationChronic(Boolean patientRegistrationChronic) {
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

    public String getPatientRegistrationBloodGroup() {
        return patientRegistrationBloodGroup;
    }

    public void setPatientRegistrationBloodGroup(String patientRegistrationBloodGroup) {
        this.patientRegistrationBloodGroup = patientRegistrationBloodGroup;
    }

    public String getPatientRegistrationEmailId() {
        return patientRegistrationEmailId;
    }

    public void setPatientRegistrationEmailId(String patientRegistrationEmailId) {
        this.patientRegistrationEmailId = patientRegistrationEmailId;
    }
}