package com.foodiehub.controller;

import java.io.IOException;

import com.foodiehub.DAOImpl.UserDAOImpl;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(com.foodiehub.utility.PasswordUtil.hashPassword(password));
        user.setAddress(address);
        user.setRole("customer");

        UserDAOImpl dao = new UserDAOImpl();
        int result = dao.addUser(user);

        if (result > 0) {
            response.sendRedirect("login.jsp?registered=1");
        } else {
            response.sendRedirect("register.jsp?error=1");
        }
    }
}