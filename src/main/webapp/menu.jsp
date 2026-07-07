<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@page import="com.foodiehub.model.Menu"%>
<%@page import="com.foodiehub.model.User"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | Menu</title>

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
    <div class="logo">🍽 Zestora</div>
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

<div class="page-shell">
<div class="menu-page">

    <h1 class="menu-title">Our Special Menu</h1>

<%
List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
Integer restaurantId = (Integer) request.getAttribute("restaurantId");
Map<Integer,Integer> cartQtyMap = (Map<Integer,Integer>) request.getAttribute("cartQtyMap");

LinkedHashSet<String> categories = new LinkedHashSet<>();
categories.add("All");
if(menuList != null){
    for(Menu m : menuList){
        categories.add(m.getCategory());
    }
}
%>

    <div class="category-tabs">
<%
boolean first = true;
for(String cat : categories){
%>
        <button type="button" class="category-tab <%= first ? "active" : "" %>"
                onclick="filterCategory('<%=cat%>', this)"><%=cat%></button>
<%
    first = false;
}
%>
    </div>

    <div class="menu-grid">

<%
if(menuList != null){
for(Menu menu : menuList){

    String imageName = menu.getItemName()
            .toLowerCase()
            .replace(" ", "") + ".png";

    int qtyInCart = 0;
    if(cartQtyMap != null && cartQtyMap.containsKey(menu.getMenuID())){
        qtyInCart = cartQtyMap.get(menu.getMenuID());
    }
%>

       <div class="menu-card" data-category="<%=menu.getCategory()%>">

    <span class="favorite">♡</span>

    <div class="image-box">
        <img class="menu-image" src="images/<%=imageName%>" alt="<%=menu.getItemName()%>">
    </div>

    <div class="menu-content">

        <h2 class="food-name"><%=menu.getItemName()%></h2>

        <p class="food-description"><%=menu.getDescription()%></p>

        <span class="category">🍽 <%=menu.getCategory()%></span>

        <div class="menu-footer">
            <div>
                <h2 class="price">₹<%= (int)menu.getPrice() %></h2>
            </div>

<%
    if(qtyInCart > 0){
%>
            <form action="${pageContext.request.contextPath}/decreaseCart" method="post">
                <input type="hidden" name="menuID" value="<%=menu.getMenuID()%>">
                <input type="hidden" name="restaurantId" value="<%=restaurantId%>">
                <div class="qty-stepper">
                    <button type="submit">−</button>
                    <span><%=qtyInCart%></span>
                    <button type="submit" formaction="${pageContext.request.contextPath}/addCart">+</button>
                </div>
            </form>
<%
    } else {
%>
            <form action="${pageContext.request.contextPath}/addCart" method="post">
                <input type="hidden" name="menuID" value="<%=menu.getMenuID()%>">
                <input type="hidden" name="restaurantId" value="<%=restaurantId%>">
                <button class="cart-btn" type="submit">🛒 Add To Cart</button>
            </form>
<%
    }
%>
        </div>

    </div>

</div>
<%
}
}
%>

   </div>

</div>
</div>

<div id="toast" class="toast">✅ Added to cart!</div>

<script>
(function(){
    var params = new URLSearchParams(window.location.search);
    if(params.get('added') === '1'){
        var toast = document.getElementById('toast');
        toast.classList.add('show');

        setTimeout(function(){
            toast.classList.remove('show');
        }, 2200);

        params.delete('added');
        var newUrl = window.location.pathname +
                (params.toString() ? '?' + params.toString() : '');
        window.history.replaceState({}, '', newUrl);
    }
})();

function filterCategory(cat, btn){
    document.querySelectorAll('.category-tab').forEach(function(t){ t.classList.remove('active'); });
    btn.classList.add('active');
    document.querySelectorAll('.menu-card').forEach(function(card){
        if(cat === 'All' || card.getAttribute('data-category') === cat){
            card.style.display = '';
        } else {
            card.style.display = 'none';
        }
    });
}
</script>

</body>
</html>