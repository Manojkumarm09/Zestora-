<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Zestora | Register</title>

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
        <p class="auth-subtitle">Create an account to start ordering</p>

<%
if(request.getParameter("error") != null){
%>
        <div class="error-banner">Registration failed. That email may already be in use.</div>
<%
}
%>

        <form action="register" method="post">

            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="username" placeholder="John Doe" required>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" placeholder="you@example.com" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" placeholder="••••••••" required>
            </div>

            <div class="form-group">
                <label>Delivery Address</label>
                <input type="text" name="address" placeholder="Street, Area, City" required>
            </div>

            <button type="submit" class="auth-btn">Create Account</button>

        </form>

        <div class="auth-footer">
            Already have an account? <a href="login.jsp">Login</a>
        </div>

    </div>
</div>

</body>
</html>