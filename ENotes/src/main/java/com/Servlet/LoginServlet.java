package com.Servlet;

import com.DAO.UserDAO;
import com.User.UserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");

        UserDetails user = new UserDetails();
        user.setEmail(email);
        user.setPassword(password);

        Connection conn = (Connection) getServletContext().getAttribute("conn");
        HttpSession session = req.getSession();

        try {
            user = UserDAO.findUser(user, conn);
            if (user != null) {
                session.setAttribute("user", user);
                resp.sendRedirect("home.jsp");
            } else {
                session.setAttribute("loginfail", "Email or Password is not correct. Try again...");
                resp.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
