package com.foodiehub.DAOImpl;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.foodiehub.DAO.OrderDAO;
import com.foodiehub.model.OrderItem;
import com.foodiehub.model.OrderTable;
import com.foodiehub.utility.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT_ORDER =
        "INSERT INTO ordertable(userID,restaurantID,orderDate,totalAmount,status,paymentMethod) VALUES(?,?,NOW(),?,?,?)";
    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO orderitem(orderID, menuID, quantity, itemTotal) VALUES(?,?,?,?)";
    private static final String GET_ORDERS_BY_USER =
    		"SELECT * FROM OrderTable WHERE userID=? ORDER BY orderDate DESC";
    private static final String UPDATE_ORDER_STATUS =
            "UPDATE ordertable SET status=? WHERE orderID=?";
    @Override
    public int placeOrder(OrderTable order) {

        int status = 0;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt =
                con.prepareStatement(INSERT_ORDER)) {

            pstmt.setInt(1, order.getUserID());
            pstmt.setInt(2, order.getRestaurantID());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMethod());

            status = pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    @Override
    public int placeOrderAndGetId(OrderTable order) {

        int orderID = 0;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt =
                con.prepareStatement(
                    INSERT_ORDER,
                    Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, order.getUserID());
            pstmt.setInt(2, order.getRestaurantID());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMethod());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
                orderID = rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return orderID;
    }
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
    public List<OrderTable> getOrdersByUser(int userID) {

        List<OrderTable> orders = new ArrayList<>();

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps =
            con.prepareStatement(GET_ORDERS_BY_USER)) {

            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                OrderTable order = new OrderTable();

                order.setOrderID(rs.getInt("orderID"));
                order.setUserID(rs.getInt("userID"));
                order.setRestaurantID(rs.getInt("restaurantID"));
                order.setOrderDate(rs.getTimestamp("orderDate"));
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("paymentMethod"));

                orders.add(order);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    @Override
    public int updateOrderStatus(int orderID, String status) {

        int result = 0;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt =
                    con.prepareStatement(UPDATE_ORDER_STATUS)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, orderID);

            result = pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
}