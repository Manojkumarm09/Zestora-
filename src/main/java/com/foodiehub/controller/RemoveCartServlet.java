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

@WebServlet("/removeCart")
public class RemoveCartServlet extends HttpServlet {

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

        CartDAOImpl dao = new CartDAOImpl();
        dao.deleteCartItem(cartID);

        response.sendRedirect("cart");
    }
}