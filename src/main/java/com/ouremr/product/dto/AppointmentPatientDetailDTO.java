package com.ouremr.product.dto;

public class AppointmentPatientDetailDTO {

    private String patientName;
    private String gender;
    private Integer patientId;
    private String emailId;
    private String mobileNo;
    private String dob;
    private String stateAndCity;
    private String apptDate;
    private String apptStartTime;
    private String apptEndTime;
    private String apptSessionType;
    private String reason;
    private String appointmentStatus;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStateAndCity() {
        return stateAndCity;
    }

    public void setStateAndCity(String stateAndCity) {
        this.stateAndCity = stateAndCity;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getApptStartTime() {
        return apptStartTime;
    }

    public void setApptStartTime(String apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public String getApptEndTime() {
        return apptEndTime;
    }

    public void setApptEndTime(String apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    public String getApptSessionType() {
        return apptSessionType;
    }

    public void setApptSessionType(String apptSessionType) {
        this.apptSessionType = apptSessionType;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
