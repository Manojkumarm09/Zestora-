package com.foodiehub.controller;

import java.io.IOException;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.model.Cart;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addCart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?error=loginRequired");
            return;
        }

        int userID = user.getUserId();
        int menuID = Integer.parseInt(request.getParameter("menuID"));
        String restaurantId = request.getParameter("restaurantId");

        Cart cart = new Cart();
        cart.setUserID(userID);
        cart.setMenuID(menuID);
        cart.setQuantity(1);

        CartDAOImpl dao = new CartDAOImpl();

        Cart existingCart = dao.getCartItem(userID, menuID);

        if (existingCart != null) {
            int newQty = existingCart.getQuantity() + 1;
            dao.updateCartItem(existingCart.getCartID(), newQty);
        } else {
            dao.addCartItem(cart);
        }

        if (restaurantId != null && !restaurantId.isEmpty()) {
            response.sendRedirect("menu?restaurantId=" + restaurantId + "&added=1");
        } else {
            response.sendRedirect("cart");
        }
    }
}