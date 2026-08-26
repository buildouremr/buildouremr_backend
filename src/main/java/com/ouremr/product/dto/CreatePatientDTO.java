package com.ouremr.product.dto;

public class CreatePatientDTO {

    private String appointmentId;
    private String patientId;

    // Patient Details
    private String patientFirstName;
    private String patientMiddleName;
    private String patientLastName;
    private String patientMobileNumber;
    private String patientEmergencyContact;
    private String patientLocation;
    private String patientEmailId;
    private String dateOfBirth;
    private String gender;
    private String height;
    private String weight;
    private String bmi;
    private String bloodGroup;

    // Appointment Details
    private Long providerId;
    private String appointmentDate;
    private String appointmentTime;
    private String appointmentTypeId;
    private String chiefComplaint;
    private String patientKnowAllergies;
    private String patientChronicHistory;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public String getPatientEmergencyContact() {
        return patientEmergencyContact;
    }

    public void setPatientEmergencyContact(String patientEmergencyContact) {
        this.patientEmergencyContact = patientEmergencyContact;
    }

    public String getPatientLocation() {
        return patientLocation;
    }

    public void setPatientLocation(String patientLocation) {
        this.patientLocation = patientLocation;
    }

    public String getPatientEmailId() {
        return patientEmailId;
    }

    public void setPatientEmailId(String patientEmailId) {
        this.patientEmailId = patientEmailId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentTypeId() {
        return appointmentTypeId;
    }

    public void setAppointmentTypeId(String appointmentTypeId) {
        this.appointmentTypeId = appointmentTypeId;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getPatientKnowAllergies() {
        return patientKnowAllergies;
    }

    public void setPatientKnowAllergies(String patientKnowAllergies) {
        this.patientKnowAllergies = patientKnowAllergies;
    }

    public String getPatientChronicHistory() {
        return patientChronicHistory;
    }

    public void setPatientChronicHistory(String patientChronicHistory) {
        this.patientChronicHistory = patientChronicHistory;
    }
}
