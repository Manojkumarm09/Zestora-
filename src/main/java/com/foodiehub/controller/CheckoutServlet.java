package com.foodiehub.controller;

import java.io.IOException;
import java.util.List;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.DAOImpl.MenuDAOImpl;
import com.foodiehub.DAOImpl.OrderDAOImpl;
import com.foodiehub.DAOImpl.OrderItemDAOImpl;
import com.foodiehub.model.Cart;
import com.foodiehub.model.Menu;
import com.foodiehub.model.OrderItem;
import com.foodiehub.model.OrderTable;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

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

        int userID = user.getUserId();
        CartDAOImpl cartDao = new CartDAOImpl();
        MenuDAOImpl menuDao = new MenuDAOImpl();
        OrderDAOImpl orderDao = new OrderDAOImpl();
        OrderItemDAOImpl orderItemDao = new OrderItemDAOImpl();

        List<Cart> cartItems = cartDao.getCartItems(userID);

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "Cash";
        }

        double subtotal = 0;
        for (Cart cart : cartItems) {
            Menu menu = menuDao.getMenu(cart.getMenuID());
            subtotal += menu.getPrice() * cart.getQuantity();
        }

        double deliveryFee = 40;
        double gst = subtotal * 0.05;
        double totalAmount = subtotal + deliveryFee + gst;

        OrderTable order = new OrderTable();
        order.setUserID(userID);

        Menu firstMenu = menuDao.getMenu(cartItems.get(0).getMenuID());
        order.setRestaurantID(firstMenu.getRestaurantID());
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");
        order.setPaymentMethod(paymentMethod);

        int orderID = orderDao.placeOrderAndGetId(order);

        for (Cart cart : cartItems) {
            Menu menu = menuDao.getMenu(cart.getMenuID());

            OrderItem item = new OrderItem();
            item.setOrderID(orderID);
            item.setMenuID(cart.getMenuID());
            item.setQuantity(cart.getQuantity());
            item.setItemTotal(menu.getPrice() * cart.getQuantity());

            orderItemDao.addOrderItem(item);
        }

        cartDao.clearCart(userID);

        session.setAttribute("lastOrderID", orderID);

        response.sendRedirect("orderSuccess.jsp");
    }
}