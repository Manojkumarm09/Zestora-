package com.foodiehub.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodiehub.DAO.RestaurantDAO;
import com.foodiehub.model.Restaurant;
import com.foodiehub.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private static final String GET_ALL_RESTAURANTS =
            "SELECT * FROM Restaurant WHERE isActive = 1";

    private static final String GET_RESTAURANT =
            "SELECT * FROM Restaurant WHERE restaurantID = ?";

    @Override
    public List<Restaurant> getAllRestaurants() {

        List<Restaurant> restaurantList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(GET_ALL_RESTAURANTS);

            while (rs.next()) {

                Restaurant restaurant = new Restaurant();

                restaurant.setRestaurantID(rs.getInt("restaurantID"));
                restaurant.setName(rs.getString("name"));
                restaurant.setCuisineType(rs.getString("cuisineType"));
                restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setAdminUserID(rs.getInt("adminUserID"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setActive(rs.getBoolean("isActive"));

                restaurantList.add(restaurant);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

    @Override
    public Restaurant getRestaurant(int restaurantID) {

        Restaurant restaurant = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(GET_RESTAURANT)) {

            ps.setInt(1, restaurantID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                restaurant = new Restaurant();

                restaurant.setRestaurantID(rs.getInt("restaurantID"));
                restaurant.setName(rs.getString("name"));
                restaurant.setCuisineType(rs.getString("cuisineType"));
                restaurant.setDeliveryTime(rs.getInt("deliveryTime"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setAdminUserID(rs.getInt("adminUserID"));
                restaurant.setRating(rs.getDouble("rating"));
                restaurant.setActive(rs.getBoolean("isActive"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurant;
    }
}