package com.foodiehub.utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    private static final String URL =
            "jdbc:mysql://localhost:3306/instant_food";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "your_db_password_here";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}