package com.foodiehub.DAO;

import java.util.List;

import com.foodiehub.model.Cart;

public interface CartDAO {

    int addCartItem(Cart cart);

    List<Cart> getCartItems(int userID);

    int updateCartItem(int cartID, int quantity);

    int deleteCartItem(int cartID);
    
    Cart getCartItem(int userID, int menuID);
    
    int clearCart(int userID);
    
}
