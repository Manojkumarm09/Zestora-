package com.foodiehub.controller;

import java.io.IOException;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int cartID = Integer.parseInt(request.getParameter("cartID"));
        int currentQty = Integer.parseInt(request.getParameter("qty"));
        String action = request.getParameter("action");

        int newQty = currentQty;

        if ("increase".equals(action)) {
            newQty = currentQty + 1;
        } else if ("decrease".equals(action)) {
            newQty = currentQty - 1;
        }

        CartDAOImpl dao = new CartDAOImpl();

        if (newQty <= 0) {
            dao.deleteCartItem(cartID);
        } else {
            dao.updateCartItem(cartID, newQty);
        }

        response.sendRedirect("cart");
    }
}