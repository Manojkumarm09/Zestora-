<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="com.foodiehub.model.User"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | Order Placed</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>

<%
User loggedUser = (User) session.getAttribute("user");
Integer lastOrderID = (Integer) session.getAttribute("lastOrderID");
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

<div class="success-wrap">
    <div class="success-card">
        <div class="success-icon">🎉</div>
        <h1>Order Placed Successfully!</h1>
<% if(lastOrderID != null){ %>
        <p class="order-id-line">Order #<%=lastOrderID%></p>
<% } %>
        <p>Your food is being prepared and will be on its way shortly.</p>
        <p class="eta-line">⏱ Estimated Delivery: 30–45 mins</p>

        <div class="success-actions">
            <a href="restaurants" class="btn-secondary">Continue Shopping</a>
            <a href="orders" class="btn-primary">View My Orders</a>
        </div>
    </div>
</div>

</body>
</html>