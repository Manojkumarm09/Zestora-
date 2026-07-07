package com.foodiehub.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.foodiehub.DAOImpl.OrderDAOImpl;
import com.foodiehub.DAOImpl.OrderItemDAOImpl;
import com.foodiehub.model.OrderItem;
import com.foodiehub.model.OrderTable;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orders")
public class OrderHistoryServlet extends HttpServlet {

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

        OrderDAOImpl orderDao = new OrderDAOImpl();
        OrderItemDAOImpl orderItemDao = new OrderItemDAOImpl();

        List<OrderTable> orders = orderDao.getOrdersByUser(userID);

        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();

        for (OrderTable order : orders) {
            List<OrderItem> items = orderItemDao.getItemsByOrderId(order.getOrderID());
            orderItemsMap.put(order.getOrderID(), items);
        }

        request.setAttribute("orders", orders);
        request.setAttribute("orderItemsMap", orderItemsMap);

        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}