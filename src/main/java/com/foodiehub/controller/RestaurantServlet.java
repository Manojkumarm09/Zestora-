package com.foodiehub.controller;

import java.io.IOException;
import java.util.List;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.DAOImpl.RestaurantDAOImpl;
import com.foodiehub.model.Restaurant;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        RestaurantDAOImpl dao = new RestaurantDAOImpl();
        List<Restaurant> restaurantList = dao.getAllRestaurants();

        int cartCount = 0;

        if (user != null) {
            CartDAOImpl cartDao = new CartDAOImpl();
            cartCount = cartDao.getCartItems(user.getUserId()).size();
        }

        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("cartCount", cartCount);

        request.getRequestDispatcher("restaurant.jsp")
               .forward(request, response);
    }
}