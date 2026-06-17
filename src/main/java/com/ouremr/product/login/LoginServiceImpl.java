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
    public UserLogin authenticateUser(String userEmail, String password) {

        if (!HUtil.isValidString(userEmail) || !HUtil.isValidString(password)) {
            return null;
        }

        UserLogin user = userLoginRepository.findByUserEmail(userEmail);

        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(password, user.getUserPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public UserLogin getUser(String userEmail) {
        return userLoginRepository.findByUserEmail(userEmail);
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