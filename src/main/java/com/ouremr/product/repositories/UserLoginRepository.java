package com.ouremr.product.repositories;

import com.ouremr.product.tables.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUserEmail(String userEmail);
}