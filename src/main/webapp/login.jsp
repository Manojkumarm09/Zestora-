<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | Login</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<link rel="icon" type="image/x-icon" href="favicon.ico">
</head>
<body>

<nav class="navbar">
    <div class="logo">🍽 Zestora</div>
</nav>

<div class="auth-wrap">
    <div class="auth-card">

        <div class="auth-logo">🍽 Zestora</div>
        <p class="auth-subtitle">Welcome back — log in to continue</p>

<%
String error = request.getParameter("error");
if(error != null){
    String message;
    if("blocked".equals(error)) message = "Your account has been blocked after 3 failed attempts.";
    else if("notfound".equals(error)) message = "No account found with that email.";
    else if("loginRequired".equals(error)) message = "Please login to continue with your order.";
    else message = "Invalid password. Attempts remaining: " + request.getParameter("left");
%>
        <div class="error-banner"><%=message%></div>
<%
}
if(request.getParameter("registered") != null){
%>
        <div class="success-banner">Account created successfully! Please log in.</div>
<%
}
%>

        <form action="login" method="post">

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="you@example.com" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="••••••••" required>
            </div>

            <button type="submit" class="auth-btn">Login</button>

        </form>

        <div class="auth-footer">
            Don't have an account? <a href="register.jsp">Register</a>
        </div>

    </div>
</div>

</body>
</html>