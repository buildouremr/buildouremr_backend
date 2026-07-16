package com.ouremr.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OTPVerify{

    private String key;

    @JsonProperty("OTP")
    private String OTP;

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getOTP() { return OTP; }
    public void setOTP(String OTP) { this.OTP = OTP; }
}
