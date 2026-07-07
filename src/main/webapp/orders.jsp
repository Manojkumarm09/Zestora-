<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.foodiehub.model.OrderTable" %>
<%@ page import="com.foodiehub.model.OrderItem" %>
<%@ page import="com.foodiehub.model.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | My Orders</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>

<%
User loggedUser = (User) session.getAttribute("user");
%>

<nav class="navbar">
    <div class="logo">🍽 Zestora</div>
    <div class="nav-links">
        <a href="restaurants">Home</a>
        <a href="restaurants">Restaurants</a>
        <a href="cart">Cart</a>
        <a href="orders">My Orders</a>
<% if(loggedUser != null){ %>
        <span class="nav-welcome">Hi, <%=loggedUser.getUserName()%></span>
<% } %>
        <a href="logout">Logout</a>
    </div>
</nav>

<div class="page-shell">
<div class="menu-page">

<h1 class="menu-title">My Orders</h1>

<%
List<OrderTable> orders = (List<OrderTable>) request.getAttribute("orders");
Map<Integer, List<OrderItem>> orderItemsMap =
        (Map<Integer, List<OrderItem>>) request.getAttribute("orderItemsMap");

if(orders == null || orders.isEmpty()){
%>

    <div class="empty-state">
        <h2>No orders yet</h2>
        <p>Your placed orders will show up here.</p>
        <br>
        <a href="restaurants" class="hero-btn">Browse Restaurants</a>
    </div>

<%
} else {
%>

<div class="orders-list">
<%
for(OrderTable order : orders){

    String statusClass = "status-pending";
    String status = order.getStatus() == null ? "Pending" : order.getStatus();

    if(status.equalsIgnoreCase("Delivered")) statusClass = "status-delivered";
    else if(status.equalsIgnoreCase("Cancelled")) statusClass = "status-cancelled";

    List<OrderItem> items = orderItemsMap != null ? orderItemsMap.get(order.getOrderID()) : null;
%>
    <div class="order-card">

        <div class="order-card-header">
            <span class="order-id">Order #<%=order.getOrderID()%></span>
            <span class="status-badge <%=statusClass%>"><%=status%></span>
        </div>

        <div class="order-meta">
            <span><strong>Date:</strong> <%=order.getOrderDate()%></span>
            <span><strong>Payment:</strong> <%=order.getPaymentMethod()%></span>
        </div>

<%
        if(items != null && !items.isEmpty()){
%>
        <div class="order-items-list">
<%
            for(OrderItem item : items){
%>
            <div class="order-item-row">
                <span><%=item.getItemName()%> &times; <%=item.getQuantity()%></span>
                <span>₹<%=(int)item.getItemTotal()%></span>
            </div>
<%
            }
%>
        </div>
<%
        }
%>

        <div class="order-total">Total: ₹<%=(int)order.getTotalAmount()%></div>

<div class="order-actions">

<%
        if(status.equalsIgnoreCase("Pending")){
%>
            <form action="${pageContext.request.contextPath}/cancelOrder" method="post">
                <input type="hidden" name="orderID" value="<%=order.getOrderID()%>">
                <button type="submit" class="cancel-order-btn">Cancel Order</button>
            </form>
<%
        } else {
%>
            <form action="${pageContext.request.contextPath}/reorder" method="post">
                <input type="hidden" name="orderID" value="<%=order.getOrderID()%>">
                <button type="submit" class="reorder-btn">Reorder</button>
            </form>
<%
        }
%>

        </div>

    </div>
<%
}
%>
</div>

<%
}
%>

</div>
</div>

</body>
</html>