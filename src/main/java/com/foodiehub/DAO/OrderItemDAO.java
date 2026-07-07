package com.foodiehub.DAO;

import java.util.List;

import com.foodiehub.model.OrderItem;

public interface OrderItemDAO {

    int addOrderItem(OrderItem item);
    
    List<OrderItem> getItemsByOrderId(int orderID);

}