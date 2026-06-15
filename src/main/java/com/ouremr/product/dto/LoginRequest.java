package com.ouremr.product.dto;

public class LoginRequest {

    private String userEmail;
    private String password;

    private String OTP;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserName(String userName) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
