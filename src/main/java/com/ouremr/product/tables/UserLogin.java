package com.ouremr.product.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_login")
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @JsonIgnore
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "user_is_locked", nullable = false)
    private Boolean isLocked = false;

    @Column(name = "user_failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;

    @Column(name = "user_created_date", nullable = false)
    private OffsetDateTime userCreatedDate;

    @Column(name = "user_modified_date")
    private OffsetDateTime userModifiedDate;

    // --- Getters & Setters ---

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public OffsetDateTime getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(OffsetDateTime userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public OffsetDateTime getUserModifiedDate() {
        return userModifiedDate;
    }

    public void setUserModifiedDate(OffsetDateTime userModifiedDate) {
        this.userModifiedDate = userModifiedDate;
    }
}