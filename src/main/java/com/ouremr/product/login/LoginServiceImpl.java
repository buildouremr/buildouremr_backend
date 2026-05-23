package com.ouremr.product.login;

import com.ouremr.product.repositories.UserLoginRepository;
import com.ouremr.product.tables.UserLogin;
import com.ouremr.product.util.HUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String authenticateUser(String userName, String password) {

        if (!HUtil.isValidString(userName) || !HUtil.isValidString(password)) {
            return "INVALID_INPUT";
        }

        UserLogin user = userLoginRepository.findByUserName(userName);

        System.out.println("user :: "+user);
        if (user == null) {
            return "INVALID_CREDENTIALS";
        }

        System.out.println("password :: "+password);
        System.out.println("user.getUserPassword() :: "+user.getUserPassword());
        // 🔐 SECURE PASSWORD CHECK
        if (passwordEncoder.matches(password, user.getUserPassword())) {
            return "SUCCESS";
        } else {
            return "INVALID_CREDENTIALS";
        }
    }

    @Override
    public UserLogin getUser(String userName) {
        return userLoginRepository.findByUserName(userName);
    }

    @Override
    public void save(UserLogin user) {
        if (!user.getUserPassword().startsWith("$2a$")) {
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        }
        userLoginRepository.save(user);
    }

    @Override
    public UserLogin register(UserLogin user) {

        // 🔐 Encrypt password before saving
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        return userLoginRepository.save(user);
    }
}