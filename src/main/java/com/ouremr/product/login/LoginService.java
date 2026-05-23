package com.ouremr.product.login;

import com.ouremr.product.tables.UserLogin;

import java.util.List;

public interface LoginService {

    String authenticateUser(String userName, String password);
    UserLogin getUser(String userName);
    void save(UserLogin user);

    UserLogin register(UserLogin user);
}