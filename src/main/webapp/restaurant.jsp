<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.foodiehub.model.Restaurant" %>
<%@ page import="com.foodiehub.model.User" %>

<html>
<head>

<meta charset="UTF-8">
<meta name="viewport"
content="width=device-width, initial-scale=1.0">

<title>Zestora</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" type="image/x-icon" href="favicon.ico">
</head>

<body>

<%
User loggedUser = (User) session.getAttribute("user");
Integer cartCount = (Integer) request.getAttribute("cartCount");
%>

<nav class="navbar">

    <div class="logo">
        🍽 Zestora
    </div>
 <div class="nav-search">
        <input type="text" id="restaurantSearch" class="nav-search-input"
               placeholder="Search restaurants or cuisine..."
               onkeyup="filterRestaurants()">
    </div>


    <div class="nav-links">
        <a href="restaurants">Home</a>
        <a href="restaurants">Restaurants</a>
<% if(loggedUser != null){ %>
        <a href="cart" class="cart-link">
            Cart
<% if(cartCount != null && cartCount > 0){ %>
            <span class="cart-badge"><%=cartCount%></span>
<% } %>
        </a>
        <a href="orders">My Orders</a>
        <span class="nav-welcome">Hi, <%=loggedUser.getUserName()%></span>
        <a href="logout">Logout</a>
<% } else { %>
        <a href="login.jsp">Login</a>
        <a href="register.jsp">Register</a>
<% } %>
    </div>

</nav>
<section class="hero">

    <div class="hero-content">

        <h1>Discover Bengaluru's Finest Restaurants</h1>

        <p>
            Order delicious food from premium restaurants
            delivered fresh to your doorstep.
        </p>

        <a href="#restaurants" class="hero-btn">
            Explore Restaurants
        </a>

        <div class="hero-box">
            ⭐ 11+ Premium Restaurants |
            🚀 Fast Delivery |
            🍽 Fresh Food
        </div>

    </div>
    <img src="images/zingerburger.png" class="floating burger">
     <img src="images/vegsupremepizza.png" class="floating pizza">
     <img src="images/chickenbiryani.png" class="floating biryani">
     <img src="images/masaladose.png" class="floating bbq">



</section>
<section class="promo-banner">
    <div class="promo-banner-content">
        <h2>Real Food. Real Flavors. Real Fast.</h2>
        <p>Handpicked dishes from Bengaluru's most loved kitchens, plated fresh and delivered hot.</p>
    </div>
</section>

<div id="restaurants" class="restaurants-section">

<h2 class="section-title">

Popular Restaurants

</h2>


<div class="restaurant-grid" id="restaurantGrid">

<%
List<Restaurant> restaurants =
(List<Restaurant>)request.getAttribute("restaurantList");

for(Restaurant r : restaurants){
%>

<div class="restaurant-card"
     data-name="<%= r.getName().toLowerCase() %>"
     data-cuisine="<%= r.getCuisineType().toLowerCase() %>">

    <img src="images/<%= r.getName().replace(" ", "").toLowerCase() %>.jpg"
         alt="<%= r.getName() %>">

    <div class="restaurant-info">

        <h3>
            <a href="menu?restaurantId=<%= r.getRestaurantID() %>">
                <%= r.getName() %>
            </a>
        </h3>
<p class="cuisine">
    <strong>Cuisine:</strong> <%= r.getCuisineType() %>
</p>

<div class="restaurant-meta">

    <span class="rating">
        ⭐ <%= r.getRating() %>
    </span>

    <span class="time">
        🕒 <%= r.getDeliveryTime() %> mins
    </span>

</div>

<p class="location">
    📍 <%= r.getAddress() %>
</p>

        <a href="menu?restaurantId=<%= r.getRestaurantID() %>"
   class="menu-btn">
   View Menu →
</a>
    </div>

</div>

<%
}
%>

</div>

<p id="noResults" class="no-results" style="display:none;">
    No restaurants match your search.
</p>

</div>

<footer class="footer">
    <div class="footer-content">
        <div class="logo">🍽 Zestora</div>
        <p>Order delicious food from your favorite restaurants, delivered fresh.</p>
        <p class="footer-copy">© 2026 Zestora. Built for learning purposes.</p>
    </div>
</footer>

<script>
function filterRestaurants(){
    var query = document.getElementById('restaurantSearch').value.toLowerCase();
    var cards = document.querySelectorAll('#restaurantGrid .restaurant-card');
    var visibleCount = 0;

    cards.forEach(function(card){
        var name = card.getAttribute('data-name');
        var cuisine = card.getAttribute('data-cuisine');
        if(name.indexOf(query) !== -1 || cuisine.indexOf(query) !== -1){
            card.style.display = '';
            visibleCount++;
        } else {
            card.style.display = 'none';
        }
    });

    document.getElementById('noResults').style.display =
        visibleCount === 0 ? 'block' : 'none';
}
</script>

</body>
</html>