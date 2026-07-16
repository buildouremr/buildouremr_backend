package com.ouremr.product.login;

import com.ouremr.product.dto.LoginRequest;
import com.ouremr.product.dto.LoginResponseDTO;
import com.ouremr.product.dto.OTPVerify;
import com.ouremr.product.emrbean.EMRResponseBean;
import com.ouremr.product.otp.OTPService;
import com.ouremr.product.security.JWTUtil;
import com.ouremr.product.tables.UserLogin;
import com.ouremr.product.util.HUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<EMRResponseBean> register(@Valid @RequestBody UserLogin user) {
        EMRResponseBean response = new EMRResponseBean();

        if (!HUtil.isValidString(user.getUserEmail()) || !HUtil.isValidString(user.getUserPassword())) {
            response.setData("Email and password are required");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if user already exists
        UserLogin existingUser = loginService.getUser(user.getUserEmail());
        if (existingUser != null) {
            response.setData("A user with this email already exists");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        loginService.register(user);
        response.setData("User registered successfully");
        response.setStatus("SUCCESS");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticate user and set JWT as HttpOnly cookie.
     */
    @PostMapping("/login")
    public ResponseEntity<EMRResponseBean> login(@Valid @RequestBody LoginRequest request,
                                                  HttpServletResponse httpResponse) {
        EMRResponseBean response = new EMRResponseBean();

        // Find user first to track failed attempts
        UserLogin userForAttempts = loginService.getUser(request.getUserEmail());

        // Check if account is locked
        if (userForAttempts != null && Boolean.TRUE.equals(userForAttempts.getIsLocked())) {
            response.setData("Account is locked due to too many failed attempts. Contact support.");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Check if account is inactive
        if (userForAttempts != null && userForAttempts.getIsActive() != null && !userForAttempts.getIsActive()) {
            response.setData("Account is inactive. Contact support.");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        UserLogin user = loginService.authenticateUser(request.getUserEmail(), request.getPassword());

        if (user != null) {
            // Reset failed attempts on successful login
            if (user.getFailedLoginAttempts() != null && user.getFailedLoginAttempts() > 0) {
                loginService.resetFailedAttempts(user);
            }

            // Generate JWT and set as HttpOnly cookie
            String token = jwtUtil.generateToken(
                    request.getUserEmail(),
                    user.getUserId(),
                    user.getUserName()
            );
            jwtUtil.setTokenCookie(httpResponse, token);

            // Return user info (token is NOT in the response body)
            LoginResponseDTO loginResponse = new LoginResponseDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getUserEmail()
            );
            response.setData(loginResponse);
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);

        } else {
            // Increment failed attempts
            if (userForAttempts != null) {
                loginService.incrementFailedAttempts(userForAttempts);
            }

            response.setData("Invalid email or password");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Logout user by clearing the JWT cookie.
     */
    @PostMapping("/logout")
    public ResponseEntity<EMRResponseBean> logout(HttpServletResponse httpResponse) {
        jwtUtil.clearTokenCookie(httpResponse);
        EMRResponseBean response = new EMRResponseBean();
        response.setData("Logged out successfully");
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Get current authenticated user info (for page refresh / session check).
     * This endpoint requires a valid JWT cookie.
     */
    @GetMapping("/me")
    public ResponseEntity<EMRResponseBean> getCurrentUser(
            @CookieValue(value = "EMR_AUTH_TOKEN", required = false) String token) {
        EMRResponseBean response = new EMRResponseBean();

        if (token == null || !jwtUtil.validateToken(token)) {
            response.setData("Not authenticated");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String email = jwtUtil.extractUsername(token);
        Long userId = jwtUtil.extractUserId(token);
        UserLogin user = loginService.getUser(email);

        if (user == null) {
            response.setData("User not found");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        LoginResponseDTO userInfo = new LoginResponseDTO(userId, user.getUserName(), email);
        response.setData(userInfo);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Send OTP to email or mobile for forgot password.
     */
    @PostMapping("/forgot-password/send-otp")
    public ResponseEntity<EMRResponseBean> sendOtp(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();

        if (!HUtil.isValidString(request.getUserEmail())) {
            response.setData("Email address is required");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Verify user exists before sending OTP
        UserLogin user = loginService.getUser(request.getUserEmail());
        if (user == null) {
            // Return success to prevent email enumeration
            response.setData("OTP_SENT");
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
        }

        try {
            otpService.generateOtp(request.getUserEmail());
            response.setData("OTP_SENT");
            response.setStatus("SUCCESS");
        } catch (RuntimeException e) {
            response.setData("WAIT_30_SECONDS");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Verify the OTP entered by the user.
     */
    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<EMRResponseBean> verifyOtp(@RequestBody OTPVerify request) {
        EMRResponseBean response = new EMRResponseBean();

        if (!HUtil.isValidString(request.getKey()) || !HUtil.isValidString(request.getOTP())) {
            response.setData("Email and OTP are required");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        boolean isValid = otpService.verifyOtp(request.getKey(), request.getOTP());

        if (isValid) {
            response.setData("OTP_VALID");
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
        } else {
            response.setData("OTP_INVALID");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Reset the password after OTP verification.
     * REQUIRES prior OTP verification via Redis flag.
     */
    @PostMapping("/forgot-password/reset")
    public ResponseEntity<EMRResponseBean> resetPassword(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();

        if (!HUtil.isValidString(request.getUserEmail()) || !HUtil.isValidString(request.getPassword())) {
            response.setData("Email and new password are required");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // SECURITY: Verify OTP was verified before allowing reset
        if (!otpService.isVerified(request.getUserEmail())) {
            response.setData("OTP verification required before password reset");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        UserLogin user = loginService.getUser(request.getUserEmail());
        if (user == null) {
            response.setData("User not found");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Hash and save the new password
        user.setUserPassword(passwordEncoder.encode(request.getPassword()));
        loginService.save(user);

        // Clear the verification flag so it cannot be reused
        otpService.clearVerification(request.getUserEmail());

        // Reset failed login attempts and unlock the account
        loginService.resetFailedAttempts(user);

        response.setData("PASSWORD_UPDATED");
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    /**
     * Resend OTP (with rate limiting via Redis lock).
     */
    @PostMapping("/forgot-password/resend-otp")
    public ResponseEntity<EMRResponseBean> resendOtp(@RequestBody LoginRequest request) {
        EMRResponseBean response = new EMRResponseBean();

        if (!HUtil.isValidString(request.getUserEmail())) {
            response.setData("Email address is required");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            otpService.generateOtp(request.getUserEmail());
            response.setData("OTP_SENT");
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setData("WAIT_30_SECONDS");
            response.setStatus("FAILED");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
        }
    }
}
