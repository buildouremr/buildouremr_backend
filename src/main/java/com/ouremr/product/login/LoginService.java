package com.ouremr.product.login;

import com.ouremr.product.tables.UserLogin;

public interface LoginService {

    UserLogin authenticateUser(String userEmail, String password);
    UserLogin getUser(String userEmail);
    void save(UserLogin user);
    UserLogin register(UserLogin user);
    void resetFailedAttempts(UserLogin user);
    void incrementFailedAttempts(UserLogin user);
}