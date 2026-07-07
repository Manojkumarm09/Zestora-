package com.foodiehub.DAO;

import java.util.List;

import com.foodiehub.model.OrderItem;
import com.foodiehub.model.OrderTable;

public interface OrderDAO {

    int placeOrder(OrderTable order);

    int placeOrderAndGetId(OrderTable order);
    
    int addOrderItem(OrderItem item);
    
    List<OrderTable> getOrdersByUser(int userID);
    
    int updateOrderStatus(int orderID, String status);


}