package com.foodiehub.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodiehub.DAO.MenuDAO;
import com.foodiehub.model.Menu;
import com.foodiehub.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO{
	
	private static final String GET_MENU_BY_RESTAURANT =
	  "SELECT * FROM Menu WHERE restaurantID=? AND isAvailable=1";
	
	@Override
	public List<Menu> getMenuByRestaurantId(int restaurantId) {

	    List<Menu> menuList = new ArrayList<>();

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement psmt =
	                con.prepareStatement(GET_MENU_BY_RESTAURANT)) {

	        psmt.setInt(1, restaurantId);

	        ResultSet rs = psmt.executeQuery();

	        while(rs.next()) {

	            Menu menu = new Menu();

	            menu.setMenuID(rs.getInt("menuID"));
	            menu.setRestaurantID(rs.getInt("restaurantID"));
	            menu.setItemName(rs.getString("itemName"));
	            menu.setDescription(rs.getString("description"));
	            menu.setPrice(rs.getDouble("price"));
	            menu.setAvailable(rs.getBoolean("isAvailable"));
	            menu.setCategory(rs.getString("category"));

	            menuList.add(menu);
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return menuList;
	}
	@Override
	public Menu getMenu(int menuID) {

	    Menu menu = null;

	    try(Connection con = DBConnection.getConnection()) {

	        String sql =
	                "SELECT * FROM Menu WHERE menuID=?";

	        PreparedStatement pstmt =
	                con.prepareStatement(sql);

	        pstmt.setInt(1, menuID);

	        ResultSet rs = pstmt.executeQuery();

	        if(rs.next()) {

	            menu = new Menu();

	            menu.setMenuID(rs.getInt("menuID"));
	            menu.setRestaurantID(rs.getInt("restaurantID"));
	            menu.setItemName(rs.getString("itemName"));
	            menu.setDescription(rs.getString("description"));
	            menu.setPrice(rs.getDouble("price"));
	            menu.setAvailable(rs.getBoolean("isAvailable"));
	            menu.setCategory(rs.getString("category"));
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return menu;
	}
	

}
