package com.ouremr.product.login;

import com.ouremr.product.dto.LoginRequest;
import com.ouremr.product.dto.OTPVerify;
import com.ouremr.product.emrbean.EMRResponseBean;
import com.ouremr.product.otp.OTPService;
import com.ouremr.product.security.JWTUtil;
import com.ouremr.product.tables.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/login")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    OTPService otpService;

    @Autowired
    JWTUtil jwtUtil;

    /** This API is to register the users **/
    @PostMapping("/register")
    public String register(@RequestBody UserLogin user) {
        loginService.register(user);
        return "User Registered Successfully";
    }

    /** This API is to login for users **/
    @PostMapping("/login")
    public EMRResponseBean login(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();
        String result = loginService.authenticateUser(request.getUserEmail(), request.getPassword());
        if ("SUCCESS".equals(result)) {
            String token = jwtUtil.generateToken(request.getUserEmail());
            response.setData(token);
        } else {
            response.setData("INVALID_CREDENTIALS");
        }
        return response;
    }

    @PostMapping("/forgot-password/send-otp")
    public EMRResponseBean sendOtp(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();
        otpService.generateOtp(request.getUserEmail());
        response.setData("OTP_SENT");
        return response;
    }

    @PostMapping("/forgot-password/verify-otp")
    public EMRResponseBean verifyOtp(@RequestBody OTPVerify request) {
        EMRResponseBean response = new EMRResponseBean();
        boolean isValid = otpService.verifyOtp(request.getKey(), request.getOTP());
        response.setData(isValid ? "OTP_VALID" : "OTP_INVALID");
        return response;
    }

    @PostMapping("/forgot-password/reset")
    public EMRResponseBean resetPassword(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();
        UserLogin user = loginService.getUser(request.getUserEmail());
        user.setUserPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        loginService.save(user);
        response.setData("PASSWORD_UPDATED");
        return response;
    }

    @PostMapping("/forgot-password/resend-otp")
    public EMRResponseBean resendOtp(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();
        try {
            otpService.generateOtp(request.getUserEmail());
            response.setData("OTP_SENT");
        } catch (Exception e) {
            response.setData("WAIT_30_SECONDS");
        }
        return response;
    }
}
