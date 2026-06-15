package com.ouremr.product.login;

import com.ouremr.product.tables.UserLogin;

import java.util.List;

public interface LoginService {

    String authenticateUser(String userEmail, String password);
    UserLogin getUser(String userEmail);
    void save(UserLogin user);

    UserLogin register(UserLogin user);
}