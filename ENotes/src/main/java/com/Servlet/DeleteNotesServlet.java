package com.Servlet;

import com.DAO.NoteDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class DeleteNotesServlet extends HttpServlet {

    // EntityManager for JPA operations
    private EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        // Initialize the EntityManager
        entityManager = Persistence.createEntityManagerFactory("notes_app_pu").createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("note_id"));
        HttpSession session = req.getSession();

        // Create an instance of NoteDAO with JPA support
        NoteDAO noteDAO = new NoteDAO(entityManager);

        try {
            // Delete the note using JPA
            boolean isDeleted = noteDAO.deleteNote(id);

            if (isDeleted) {
                session.setAttribute("deleted", "Note deleted successfully");
                System.out.println("Note deleted successfully");
            } else {
                session.setAttribute("deleted", "Failed to delete note");
                System.out.println("Failed to delete note");
            }

            // Forward the request to the showNotes page
            RequestDispatcher dispatcher = req.getRequestDispatcher("showNotes");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("deleted", "Error deleting note");
            RequestDispatcher dispatcher = req.getRequestDispatcher("showNotes");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        // Close the EntityManager when the servlet is destroyed
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
