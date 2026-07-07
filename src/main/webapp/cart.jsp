<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="java.util.List"%>
<%@ page import="com.foodiehub.model.Cart"%>
<%@ page import="com.foodiehub.model.User"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | My Cart</title>

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

<h1 class="menu-title">My Cart</h1>

<%
List<Cart> cartList = (List<Cart>) request.getAttribute("cartList");

if(cartList == null || cartList.isEmpty()){
%>

    <div class="empty-state">
        <h2>Your cart is empty</h2>
        <p>Looks like you haven't added anything yet.</p>
        <br>
        <a href="restaurants" class="hero-btn">Browse Restaurants</a>
    </div>

<%
} else {

    double subtotal = 0;
    for(Cart c : cartList){
        subtotal += c.getPrice() * c.getQuantity();
    }
    double deliveryFee = subtotal >= 500 ? 0 : 40;
    double gst = subtotal * 0.05;
    double total = subtotal + deliveryFee + gst;
%>

<div class="cart-layout">

    <div class="cart-list">
<%
    for(Cart cart : cartList){
        String imageName = cart.getItemName()
                .toLowerCase()
                .replace(" ", "") + ".png";
%>
        <div class="cart-item">

            <img class="cart-item-img" src="images/<%=imageName%>" alt="<%=cart.getItemName()%>">

            <div class="cart-item-info">
                <h3><%=cart.getItemName()%></h3>
                <span class="cart-item-price">₹<%=(int)cart.getPrice()%> each</span>
            </div>

            <div class="cart-item-actions">

                <form action="${pageContext.request.contextPath}/updateCart" method="post">
                    <input type="hidden" name="cartID" value="<%=cart.getCartID()%>">
                    <input type="hidden" name="qty" value="<%=cart.getQuantity()%>">
                    <div class="qty-stepper">
                        <button type="submit" name="action" value="decrease">−</button>
                        <span><%=cart.getQuantity()%></span>
                        <button type="submit" name="action" value="increase">+</button>
                    </div>
                </form>

                <form action="${pageContext.request.contextPath}/removeCart" method="post">
                    <input type="hidden" name="cartID" value="<%=cart.getCartID()%>">
                    <button type="submit" class="remove-btn" title="Remove item">🗑</button>
                </form>

            </div>

        </div>
<%
    }
%>
    </div>

    <div class="cart-summary">
        <h3>Order Summary</h3>

        <div class="summary-row">
            <span>Subtotal</span>
            <span>₹<%=(int)subtotal%></span>
        </div>

        <div class="summary-row">
            <span>Delivery Fee</span>
<% if(deliveryFee == 0){ %>
            <span class="free-delivery-tag">FREE</span>
<% } else { %>
            <span>₹<%=(int)deliveryFee%></span>
<% } %>
        </div>
<% if(deliveryFee > 0){ %>
        <p class="free-delivery-hint">Add ₹<%=(int)(500 - subtotal)%> more for free delivery!</p>
<% } %>

        <div class="summary-row">
            <span>GST (5%)</span>
            <span>₹<%=(int)gst%></span>
        </div>

        <div class="summary-row total">
            <span>Total</span>
            <span>₹<%=(int)total%></span>
        </div>

        <a href="checkoutPage" class="checkout-btn">Proceed to Checkout</a>
        <a href="restaurants" class="continue-shopping-btn">Continue Shopping</a>
    </div>

</div>

<%
}
%>

</div>
</div>

</body>
</html>