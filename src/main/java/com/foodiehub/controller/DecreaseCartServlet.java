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

@WebServlet("/decreaseCart")
public class DecreaseCartServlet extends HttpServlet {

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

        int menuID = Integer.parseInt(request.getParameter("menuID"));
        String restaurantId = request.getParameter("restaurantId");

        CartDAOImpl dao = new CartDAOImpl();
        Cart existing = dao.getCartItem(user.getUserId(), menuID);

        if (existing != null) {
            int newQty = existing.getQuantity() - 1;

            if (newQty <= 0) {
                dao.deleteCartItem(existing.getCartID());
            } else {
                dao.updateCartItem(existing.getCartID(), newQty);
            }
        }

        if (restaurantId != null && !restaurantId.isEmpty()) {
            response.sendRedirect("menu?restaurantId=" + restaurantId);
        } else {
            response.sendRedirect("cart");
        }
    }
}