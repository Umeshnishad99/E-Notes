package com.Servlet;

import com.DAO.NoteDAO;
import com.User.NoteDetails;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.io.IOException;

public class EditNotesServlet extends HttpServlet {

    private EntityManager entityManager;

    @Override
    public void init() {
        entityManager = Persistence.createEntityManagerFactory("notes_app_pu").createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int noteId = Integer.parseInt(req.getParameter("note_id")); // example: editNotes?note_id=1
            NoteDAO noteDAO = new NoteDAO(entityManager);
            NoteDetails note = noteDAO.getNoteById(noteId);

            if (note != null) {
                req.setAttribute("note", note);
                req.getRequestDispatcher("editNote.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("showNotes");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("showNotes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int noteId = Integer.parseInt(req.getParameter("noteId"));
            String title = req.getParameter("noteTitle");
            String content = req.getParameter("noteContent");

            NoteDAO noteDAO = new NoteDAO(entityManager);
            NoteDetails note = noteDAO.getNoteById(noteId);

            HttpSession session = req.getSession();

            if (note != null) {
                note.setTitle(title);
                note.setContent(content);

                boolean updated = noteDAO.editNote(note);

                if (updated) {
                    session.setAttribute("edited", "Note updated successfully.");
                } else {
                    session.setAttribute("edited", "Failed to update note.");
                }
            }

            // Check if the request is an AJAX request or a normal redirect
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                // Send a JSON response for AJAX
                resp.setContentType("application/json");
                resp.getWriter().write("{\"status\":\"success\", \"noteTitle\":\"" + title + "\", \"noteContent\":\"" + content + "\"}");
            } else {
                // Redirect to showNotes if it's not an AJAX request
                resp.sendRedirect("showNotes");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("editNote.jsp");
        }
    }

    @Override
    public void destroy() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
