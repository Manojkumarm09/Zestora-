package com.foodiehub.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.DAOImpl.MenuDAOImpl;
import com.foodiehub.model.Cart;
import com.foodiehub.model.Menu;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int restaurantId =
                Integer.parseInt(request.getParameter("restaurantId"));

        MenuDAOImpl dao = new MenuDAOImpl();
        List<Menu> menuList = dao.getMenuByRestaurantId(restaurantId);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        Map<Integer, Integer> cartQtyMap = new HashMap<>();
        int cartCount = 0;

        if (user != null) {
            CartDAOImpl cartDao = new CartDAOImpl();
            List<Cart> cartItems = cartDao.getCartItems(user.getUserId());

            for (Cart c : cartItems) {
                cartQtyMap.put(c.getMenuID(), c.getQuantity());
            }
            cartCount = cartItems.size();
        }

        request.setAttribute("menuList", menuList);
        request.setAttribute("restaurantId", restaurantId);
        request.setAttribute("cartQtyMap", cartQtyMap);
        request.setAttribute("cartCount", cartCount);

        request.getRequestDispatcher("menu.jsp")
               .forward(request, response);
    }
}