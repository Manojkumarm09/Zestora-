package com.foodiehub.DAO;

import java.util.List;


import com.foodiehub.model.Restaurant;

public interface RestaurantDAO {
	
	List<Restaurant> getAllRestaurants();
	
	Restaurant getRestaurant(int restaurantId);

}
