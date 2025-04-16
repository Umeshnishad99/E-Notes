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

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("userFullName");
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");

        UserDetails user = new UserDetails(name, email, password);

        try {
            Connection conn = (Connection) getServletContext().getAttribute("conn");
            conn.setAutoCommit(false);
            boolean flag = UserDAO.addUser(user, conn);
            HttpSession session = req.getSession();

            if (flag) {
                conn.commit();
                session.setAttribute("reg-succ", "You Registered Successfully...");
                System.out.println("User added successfully");
                resp.sendRedirect("register.jsp");
            } else {
                conn.rollback();
                session.setAttribute("reg-fail", "Registration failed. Email already exists. Try again...");
                System.out.println("Failed to add the user");
                resp.sendRedirect("register.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
