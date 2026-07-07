package com.foodiehub.controller;

import java.io.IOException;

import com.foodiehub.DAOImpl.UserDAOImpl;
import com.foodiehub.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAOImpl dao = new UserDAOImpl();
        User user = dao.getUserByEmail(email);

        if (user == null) {
            response.sendRedirect("login.jsp?error=notfound");
            return;
        }

        if (user.isBlocked()) {
            response.sendRedirect("login.jsp?error=blocked");
            return;
        }

        if (com.foodiehub.utility.PasswordUtil.verifyPassword(password, user.getPassword())) {
            dao.resetLoginAttempts(user.getUserId());

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userName", user.getUserName());

            response.sendRedirect("restaurants");

        } else {

            int attempts = user.getLoginAttempts() + 1;
            dao.updateLoginAttempts(user.getUserId(), attempts);

            if (attempts >= 3) {
                dao.blockUser(user.getUserId());
                response.sendRedirect("login.jsp?error=blocked");
            } else {
                response.sendRedirect("login.jsp?error=invalid&left=" + (3 - attempts));
            }
        }
    }
}