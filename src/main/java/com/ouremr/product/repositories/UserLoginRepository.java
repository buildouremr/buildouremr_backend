package com.ouremr.product.repositories;

import com.ouremr.product.tables.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    @Query("SELECT COUNT(u) FROM UserLogin u WHERE u.userName = :userName AND u.userPassword = :password")
    Long getAuthenticatedUser(String userName, String password);

    UserLogin findByUserNameAndUserPassword(String userName, String userPassword);

    UserLogin findByUserEmail(String userEmail);
}