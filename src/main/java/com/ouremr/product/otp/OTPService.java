package com.ouremr.product.otp;

public interface OTPService {
    void generateOtp(String key);
    boolean verifyOtp(String key, String otp);
    boolean isVerified(String key);
    void clearVerification(String key);
}
