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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userID = user.getUserId();
        CartDAOImpl dao = new CartDAOImpl();

        List<Cart> cartList = dao.getCartItems(userID);

        request.setAttribute("cartList", cartList);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}