package com.ouremr.product.otp;

import com.ouremr.product.share.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailService emailService;

    private static final int OTP_EXPIRY = 5; // minutes
    private static final int RESEND_LIMIT_SECONDS = 30;

    @Override
    public void generateOtp(String key) {

        String lockKey = "OTP_LOCK_" + key;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(lockKey))) {
            throw new RuntimeException("WAIT_BEFORE_RESEND");
        }

        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));

        redisTemplate.opsForValue().set("OTP_" + key, otp, 5, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(lockKey, "LOCK", 30, TimeUnit.SECONDS);

        // Detect email or mobile
        if (key.contains("@")) {
            emailService.sendOtp(key, otp);
        } else {
            sendSms(key, otp);
        }

        System.out.println("OTP :: " + otp);
    }

    @Override
    public boolean verifyOtp(String key, String otp) {

        System.out.println("key : "+key+" : otp : "+otp);
        String redisKey = "OTP_" + key;

        System.out.println("VERIFY KEY: [" + redisKey + "]");

        String storedOtp = redisTemplate.opsForValue().get(redisKey);

        System.out.println("storedOtp : " + storedOtp + " = otp : " + otp);

        return storedOtp != null && storedOtp.equals(otp);
    }

    public void sendSms(String mobile, String otp) {

        String url = "https://www.fast2sms.com/dev/bulkV2";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "YOUR_API_KEY");
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("variables_values", otp);
        body.add("route", "otp");
        body.add("numbers", mobile);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}