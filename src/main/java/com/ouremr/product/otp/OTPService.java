package com.ouremr.product.otp;

public interface OTPService {
    void generateOtp(String key);
    boolean verifyOtp(String key, String otp);
}
