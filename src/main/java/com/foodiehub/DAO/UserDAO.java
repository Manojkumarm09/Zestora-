package com.foodiehub.DAO;

import java.util.List;

import com.foodiehub.model.User;

public interface UserDAO {

    int addUser(User user);

    User getUser(int userId);

    List<User> getAllUsers();

    int updateUser(User user);

    int deleteUser(int userId);
    
    User getUserByEmail(String email);
    
    void updateLoginAttempts(int userId, int attempts);

    void resetLoginAttempts(int userId);

    void blockUser(int userId);
}