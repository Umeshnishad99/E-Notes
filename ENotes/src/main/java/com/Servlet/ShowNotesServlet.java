package com.Servlet;

import com.DAO.NoteDAO;
import com.User.NoteDetails;
import com.User.UserDetails;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class ShowNotesServlet extends HttpServlet {

    // EntityManager for JPA operations
    private EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        // Initialize the EntityManager with the correct persistence unit name
        entityManager = Persistence.createEntityManagerFactory("notes_app_pu").createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the user from session
        UserDetails user = (UserDetails) req.getSession().getAttribute("user");

        // If the user is not logged in, redirect to login page
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getId();  // Get the user's ID from the session

        // Create an instance of NoteDAO to interact with the database
        NoteDAO noteDAO = new NoteDAO(entityManager);

        // Fetch all notes for the user from the database
        List<NoteDetails> notesList = noteDAO.getAllNotes(userId);

        // Set the notes list as an attribute in the request to be displayed in the JSP
        req.setAttribute("notesList", notesList);

        // Forward the request to the showNotes.jsp page
        RequestDispatcher dispatcher = req.getRequestDispatcher("showNotes.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy() {
        // Close the EntityManager when the servlet is destroyed
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
