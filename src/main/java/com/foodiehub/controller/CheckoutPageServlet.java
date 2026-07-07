package com.foodiehub.controller;

import java.io.IOException;
import java.util.List;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.model.Cart;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkoutPage")
public class CheckoutPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp?error=loginRequired");
            return;
        }

        CartDAOImpl cartDao = new CartDAOImpl();
        List<Cart> cartItems = cartDao.getCartItems(user.getUserId());

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        double subtotal = 0;
        for (Cart c : cartItems) {
            subtotal += c.getPrice() * c.getQuantity();
        }
        double deliveryFee = subtotal >= 500 ? 0 : 40;
        double gst = subtotal * 0.05;
        double total = subtotal + deliveryFee + gst;

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("deliveryFee", deliveryFee);
        request.setAttribute("gst", gst);
        request.setAttribute("total", total);

        request.getRequestDispatcher("checkoutPage.jsp").forward(request, response);
    }
}