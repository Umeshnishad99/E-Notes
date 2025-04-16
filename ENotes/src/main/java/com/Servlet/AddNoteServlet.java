package com.Servlet;

import com.DAO.NoteDAO;
import com.User.NoteDetails;
import com.User.UserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;

public class AddNoteServlet extends HttpServlet {

    // EntityManager and EntityManagerFactory for JPA operations
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void init() throws ServletException {
        // Initialize the EntityManagerFactory once and create the EntityManager
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("notes_app_pu");
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            throw new ServletException("Error initializing EntityManager", e);
        }
        System.out.println("Creating EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("notes_app_pu");
        System.out.println("EntityManagerFactory created!");
    }
    
    


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String title = req.getParameter("noteTitle");
        String content = req.getParameter("noteContent");

        // Create a new NoteDetails object and set the data
        NoteDetails note = new NoteDetails();
        note.setTitle(title);
        note.setContent(content);
        note.setUser((UserDetails) session.getAttribute("user")); // Set the user for the note

        // Create a NoteDAO instance and call the addNote method to save the note
        NoteDAO noteDAO = new NoteDAO(entityManager);

        try {
            // Add the note to the database using JPA
            boolean isAdded = noteDAO.addNotes(note);

            if (isAdded) {
                session.setAttribute("note", note);
                session.setAttribute("added", "Note added successfully");
                resp.sendRedirect("addNotes.jsp");
            } else {
                session.setAttribute("added", "Failed to add note");
                resp.sendRedirect("addNotes.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("added", "Error adding note");
            resp.sendRedirect("addNotes.jsp");
        }
    }

    @Override
    public void destroy() {
        // Close the EntityManager and EntityManagerFactory when the servlet is destroyed
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
