package com.foodiehub.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.foodiehub.DAO.OrderItemDAO;
import com.foodiehub.model.OrderItem;
import com.foodiehub.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO orderitem(orderID,menuID,quantity,itemTotal) VALUES(?,?,?,?)";

    private static final String GET_ITEMS_BY_ORDER =
            "SELECT oi.orderItemID, oi.orderID, oi.menuID, oi.quantity, oi.itemTotal, " +
            "m.itemName, m.price " +
            "FROM orderitem oi " +
            "INNER JOIN Menu m ON oi.menuID = m.menuID " +
            "WHERE oi.orderID = ?";

    @Override
    public int addOrderItem(OrderItem item) {

        int status = 0;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt =
                    con.prepareStatement(INSERT_ORDER_ITEM)) {

            pstmt.setInt(1, item.getOrderID());
            pstmt.setInt(2, item.getMenuID());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getItemTotal());

            status = pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    public List<OrderItem> getItemsByOrderId(int orderID) {

        List<OrderItem> items = new ArrayList<>();

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt =
                    con.prepareStatement(GET_ITEMS_BY_ORDER)) {

            pstmt.setInt(1, orderID);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                OrderItem item = new OrderItem();
                item.setOrderItemID(rs.getInt("orderItemID"));
                item.setOrderID(rs.getInt("orderID"));
                item.setMenuID(rs.getInt("menuID"));
                item.setQuantity(rs.getInt("quantity"));
                item.setItemTotal(rs.getDouble("itemTotal"));
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));

                items.add(item);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}