package com.ouremr.product.otp;

import com.ouremr.product.share.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    private static final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailService emailService;

    @Value("${sms.api.key}")
    private String smsApiKey;

    private static final String OTP_PREFIX = "OTP_";
    private static final String OTP_LOCK_PREFIX = "OTP_LOCK_";
    private static final String OTP_VERIFIED_PREFIX = "OTP_VERIFIED_";
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int RESEND_LOCK_SECONDS = 30;
    private static final int VERIFIED_EXPIRY_MINUTES = 10;

    @Override
    public void generateOtp(String key) {

        String lockKey = OTP_LOCK_PREFIX + key;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(lockKey))) {
            throw new RuntimeException("WAIT_BEFORE_RESEND");
        }

        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));

        redisTemplate.opsForValue().set(OTP_PREFIX + key, otp, OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(lockKey, "LOCK", RESEND_LOCK_SECONDS, TimeUnit.SECONDS);

        // Detect email or mobile and send OTP accordingly
        if (key.contains("@")) {
            emailService.sendOtp(key, otp);
        } else {
            sendSms(key, otp);
        }

        log.debug("OTP generated for key: {}", key);
    }

    @Override
    public boolean verifyOtp(String key, String otp) {

        String redisKey = OTP_PREFIX + key;
        String storedOtp = redisTemplate.opsForValue().get(redisKey);

        if (storedOtp != null && storedOtp.equals(otp)) {
            // Mark this key as verified for password reset
            redisTemplate.opsForValue().set(
                OTP_VERIFIED_PREFIX + key, "VERIFIED", VERIFIED_EXPIRY_MINUTES, TimeUnit.MINUTES
            );
            // Delete the used OTP so it cannot be reused
            redisTemplate.delete(redisKey);
            return true;
        }

        return false;
    }

    @Override
    public boolean isVerified(String key) {
        String verifiedKey = OTP_VERIFIED_PREFIX + key;
        return Boolean.TRUE.equals(redisTemplate.hasKey(verifiedKey));
    }

    @Override
    public void clearVerification(String key) {
        redisTemplate.delete(OTP_VERIFIED_PREFIX + key);
    }

    /**
     * Send OTP via SMS using Fast2SMS API.
     *
     * To receive OTP on your mobile:
     * 1. Sign up at https://www.fast2sms.com
     * 2. Get your API key from the dashboard
     * 3. Set the SMS_API_KEY environment variable or update application.properties
     * 4. Enter a mobile number (without country code) in the forgot password field
     *
     * The system auto-detects email vs mobile based on whether the input contains '@'.
     * If it is a mobile number, this SMS method is called instead of email.
     */
    public void sendSms(String mobile, String otp) {

        if ("YOUR_API_KEY".equals(smsApiKey)) {
            log.warn("SMS API key not configured. OTP for mobile {} was not sent.", mobile);
            return;
        }

        String url = "https://www.fast2sms.com/dev/bulkV2";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", smsApiKey);
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("variables_values", otp);
        body.add("route", "otp");
        body.add("numbers", mobile);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
            log.info("SMS OTP sent to mobile: {}", mobile);
        } catch (Exception e) {
            log.error("Failed to send SMS OTP to mobile: {}", mobile, e);
        }
    }
}