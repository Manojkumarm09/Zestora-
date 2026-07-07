package com.foodiehub.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.foodiehub.DAO.UserDAO;
import com.foodiehub.model.User;
import com.foodiehub.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_QUERY =
            "INSERT INTO User(userName,password,email,address,role) VALUES(?,?,?,?,?)";

    private static final String GET_USER_QUERY =
            "SELECT * FROM User WHERE userId=?";

    private static final String GET_ALL_USERS_QUERY =
            "SELECT * FROM User";

    private static final String UPDATE_QUERY =
            "UPDATE User SET userName=?, password=?, email=?, address=?, role=? WHERE userId=?";

    private static final String DELETE_QUERY =
            "DELETE FROM User WHERE userId=?";
    
    private static final String GET_USER_BY_EMAIL =
            "SELECT * FROM User WHERE email=?";
    
    private static final String UPDATE_ATTEMPTS =
            "UPDATE User SET loginAttempts=? WHERE userId=?";

    private static final String RESET_ATTEMPTS =
            "UPDATE User SET loginAttempts=0 WHERE userId=?";

    private static final String BLOCK_USER =
            "UPDATE User SET isBlocked=true WHERE userId=?";

    @Override
    public int addUser(User user) {

        int status = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_QUERY)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getRole());

            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public User getUser(int userId) {

        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(GET_USER_QUERY)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                user = new User();

                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setRole(resultSet.getString("role"));
                user.setCreateDate(resultSet.getTimestamp("createDate"));
                user.setLastLoginDate(
                        resultSet.getTimestamp("lastLoginDate"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet =
                    statement.executeQuery(GET_ALL_USERS_QUERY);

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAddress(resultSet.getString("address"));
                user.setRole(resultSet.getString("role"));

                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    @Override
    public int updateUser(User user) {

        int status = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(UPDATE_QUERY)) {

            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, user.getUserId());

            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public int deleteUser(int userId) {

        int status = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setInt(1, userId);

            status = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    @Override
    public User getUserByEmail(String email) {

        User user = null;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(GET_USER_BY_EMAIL)) {

            psmt.setString(1, email);

            ResultSet rs = psmt.executeQuery();

            if(rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setLoginAttempts(rs.getInt("loginAttempts"));
                user.setBlocked(rs.getBoolean("isBlocked"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void updateLoginAttempts(int userId, int attempts) {

        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt =
                    con.prepareStatement(UPDATE_ATTEMPTS)) {

            psmt.setInt(1, attempts);
            psmt.setInt(2, userId);

            psmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetLoginAttempts(int userId) {

        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt =
                    con.prepareStatement(RESET_ATTEMPTS)) {

            psmt.setInt(1, userId);

            psmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void blockUser(int userId) {

        try(Connection con = DBConnection.getConnection();
            PreparedStatement psmt =
                    con.prepareStatement(BLOCK_USER)) {

            psmt.setInt(1, userId);

            psmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}