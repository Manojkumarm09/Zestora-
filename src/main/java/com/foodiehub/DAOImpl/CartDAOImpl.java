package com.foodiehub.DAOImpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import com.foodiehub.DAO.*;
import com.foodiehub.model.Cart;
import com.foodiehub.utility.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public  class CartDAOImpl implements CartDAO{
	
	private static final String INSERT_CART =
	        "INSERT INTO Cart(userID,menuID,quantity) VALUES(?,?,?)";

	private static final String GET_CART_ITEMS =
	        "SELECT\r\n"
	        + "    c.cartID,\r\n"
	        + "    c.userID,\r\n"
	        + "    c.menuID,\r\n"
	        + "    c.quantity,\r\n"
	        + "    m.itemName,\r\n"
	        + "    m.price\r\n"
	        + "FROM Cart c\r\n"
	        + "INNER JOIN Menu m\r\n"
	        + "ON c.menuID = m.menuID\r\n"
	        + "WHERE c.userID = ?";

	private static final String UPDATE_CART =
	        "UPDATE Cart SET quantity=? WHERE cartID=?";

	private static final String DELETE_CART =
	        "DELETE FROM Cart WHERE cartID=?";
	
	private static final String CLEAR_CART =
	        "DELETE FROM Cart WHERE userID=?";
	
	
	@Override
	public int addCartItem(Cart cart) {

	    int status = 0;

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement psmt =
	            con.prepareStatement(INSERT_CART)) {

	        psmt.setInt(1, cart.getUserID());
	        psmt.setInt(2, cart.getMenuID());
	        psmt.setInt(3, cart.getQuantity());

	        status = psmt.executeUpdate();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}
	
	
	@Override
	public List<Cart> getCartItems(int userID) {

	    List<Cart> cartList = new ArrayList<>();

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement psmt =
	                con.prepareStatement(GET_CART_ITEMS)) {

	        psmt.setInt(1, userID);

	        ResultSet rs = psmt.executeQuery();

	        while(rs.next()) {

	            Cart cart = new Cart();

	            cart.setCartID(rs.getInt("cartID"));
	            cart.setUserID(rs.getInt("userID"));
	            cart.setMenuID(rs.getInt("menuID"));
	            cart.setQuantity(rs.getInt("quantity"));
	            cart.setItemName(rs.getString("itemName"));
	            cart.setPrice(rs.getDouble("price"));
	            cartList.add(cart);
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return cartList;
	}
	
	@Override
	public int updateCartItem(int cartID, int quantity) {

	    int status = 0;

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement psmt =
	                con.prepareStatement(UPDATE_CART)) {

	        psmt.setInt(1, quantity);
	        psmt.setInt(2, cartID);

	        status = psmt.executeUpdate();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}
	
	@Override
	public int deleteCartItem(int cartID) {

	    int status = 0;

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement psmt =
	                con.prepareStatement(DELETE_CART)) {

	        psmt.setInt(1, cartID);

	        status = psmt.executeUpdate();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}
	public Cart getCartItem(int userID, int menuID) {

	    Cart cart = null;

	    try(Connection con = DBConnection.getConnection()) {

	        String sql =
	            "SELECT * FROM Cart WHERE userID=? AND menuID=?";

	        PreparedStatement pstmt =
	            con.prepareStatement(sql);

	        pstmt.setInt(1, userID);
	        pstmt.setInt(2, menuID);

	        ResultSet rs = pstmt.executeQuery();
	       

	        if(rs.next()) {

	            cart = new Cart();

	            cart.setCartID(rs.getInt("cartID"));
	            cart.setUserID(rs.getInt("userID"));
	            cart.setMenuID(rs.getInt("menuID"));
	            cart.setQuantity(rs.getInt("quantity"));
	        }

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return cart;
	}
	
	@Override
	public int clearCart(int userID) {

	    int status = 0;

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement pstmt =
	            con.prepareStatement(CLEAR_CART)) {

	        pstmt.setInt(1, userID);

	        status = pstmt.executeUpdate();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}

}
