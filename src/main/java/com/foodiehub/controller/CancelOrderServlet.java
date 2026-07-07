package com.foodiehub.controller;

import java.io.IOException;

import com.foodiehub.DAOImpl.OrderDAOImpl;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cancelOrder")
public class CancelOrderServlet extends HttpServlet {

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

        int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDAOImpl dao = new OrderDAOImpl();
        dao.updateOrderStatus(orderID, "Cancelled");

        response.sendRedirect("orders");
    }
}