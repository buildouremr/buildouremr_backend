package com.ouremr.product.login;

import com.ouremr.product.repositories.UserLoginRepository;
import com.ouremr.product.tables.UserLogin;
import com.ouremr.product.util.HUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private static final int MAX_FAILED_ATTEMPTS = 5;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserLogin authenticateUser(String userEmail, String password) {

        if (!HUtil.isValidString(userEmail) || !HUtil.isValidString(password)) {
            return null;
        }

        UserLogin user = userLoginRepository.findByUserEmail(userEmail);

        if (user == null) {
            return null;
        }

        // Check if account is active
        if (user.getIsActive() != null && !user.getIsActive()) {
            log.warn("Login attempt on inactive account: {}", userEmail);
            return null;
        }

        // Check if account is locked
        if (user.getIsLocked() != null && user.getIsLocked()) {
            log.warn("Login attempt on locked account: {}", userEmail);
            return null;
        }

        if (passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserLogin getUser(String userEmail) {
        return userLoginRepository.findByUserEmail(userEmail);
    }

    @Override
    @Transactional
    public void save(UserLogin user) {
        user.setUserModifiedDate(OffsetDateTime.now());
        userLoginRepository.save(user);
    }

    @Override
    @Transactional
    public UserLogin register(UserLogin user) {
        // Encrypt password before saving
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setUserCreatedDate(OffsetDateTime.now());
        user.setIsActive(true);
        user.setIsLocked(false);
        user.setFailedLoginAttempts(0);
        return userLoginRepository.save(user);
    }

    @Override
    @Transactional
    public void resetFailedAttempts(UserLogin user) {
        user.setFailedLoginAttempts(0);
        user.setIsLocked(false);
        user.setUserModifiedDate(OffsetDateTime.now());
        userLoginRepository.save(user);
    }

    @Override
    @Transactional
    public void incrementFailedAttempts(UserLogin user) {
        int attempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setIsLocked(true);
            log.warn("Account locked after {} failed attempts: {}", attempts, user.getUserEmail());
        }

        user.setUserModifiedDate(OffsetDateTime.now());
        userLoginRepository.save(user);
    }
}