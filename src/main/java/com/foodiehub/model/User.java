package com.foodiehub.model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String role;
    private Timestamp createDate;
    private Timestamp lastLoginDate;
    private int loginAttempts;
    private boolean isBlocked;

    // Default Constructor
    public User() {

    }

    // Constructor without ID
    public User(String userName, String password, String email,
                String address, String role) {

        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    // Full Constructor
    public User(int userId, String userName, String password,
                String email, String address, String role,
                Timestamp createDate, Timestamp lastLoginDate) {

        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.createDate = createDate;
        this.lastLoginDate = lastLoginDate;
    }

    // Getters and Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    
    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId +
                ", userName=" + userName +
                ", email=" + email +
                ", address=" + address +
                ", role=" + role + "]";
    }
}