package com.foodiehub.controller;

import java.sql.Connection;

import com.foodiehub.DAOImpl.CartDAOImpl;
import com.foodiehub.DAOImpl.MenuDAOImpl;
import com.foodiehub.DAOImpl.OrderDAOImpl;
import com.foodiehub.DAOImpl.OrderItemDAOImpl;
import com.foodiehub.utility.DBConnection;
import com.foodiehub.model.*;
public class TestConnection {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();
        OrderItem item = new OrderItem();

        item.setOrderID(6);
        item.setMenuID(1);
        item.setQuantity(2);
        item.setItemTotal(360);

        OrderItemDAOImpl dao = new OrderItemDAOImpl();

        System.out.println(dao.addOrderItem(item));
        
    }
}