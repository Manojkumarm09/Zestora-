package com.foodiehub.controller;

import java.io.IOException;
import java.util.List;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.DAOImpl.OrderItemDAOImpl;
import com.foodiehub.model.Cart;
import com.foodiehub.model.OrderItem;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/reorder")
public class ReorderServlet extends HttpServlet {

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

        int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderItemDAOImpl orderItemDao = new OrderItemDAOImpl();
        CartDAOImpl cartDao = new CartDAOImpl();

        List<OrderItem> items = orderItemDao.getItemsByOrderId(orderID);

        for (OrderItem item : items) {
            Cart existing = cartDao.getCartItem(user.getUserId(), item.getMenuID());

            if (existing != null) {
                cartDao.updateCartItem(existing.getCartID(),
                        existing.getQuantity() + item.getQuantity());
            } else {
                Cart cart = new Cart();
                cart.setUserID(user.getUserId());
                cart.setMenuID(item.getMenuID());
                cart.setQuantity(item.getQuantity());
                cartDao.addCartItem(cart);
            }
        }

        response.sendRedirect("cart");
    }
}