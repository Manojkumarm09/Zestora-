package com.foodiehub.DAO;

 import java.util.List;


import com.foodiehub.model.Menu;

public interface MenuDAO {

	List<Menu> getMenuByRestaurantId(int restaurantId);
	
	Menu getMenu(int menuID);
}