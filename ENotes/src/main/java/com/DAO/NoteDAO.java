package com.DAO;

import com.User.NoteDetails;
import com.User.UserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class NoteDAO {

    private final EntityManager entityManager;

    public NoteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Add a new note to the database
    public boolean addNotes(NoteDetails note) {
        // Validate input
        if (note.getUser() == null || note.getUser().getId() <= 0) {
            System.out.println("Error: Invalid user for the note.");
            return false;
        }

        if (note.getTitle() == null || note.getTitle().isEmpty()) {
            System.out.println("Error: Title is required.");
            return false;
        }

        if (note.getContent() == null || note.getContent().isEmpty()) {
            System.out.println("Error: Content is required.");
            return false;
        }

        // Check if user exists in the database
        UserDetails user = entityManager.find(UserDetails.class, note.getUser().getId());
        if (user == null) {
            System.out.println("Error: User with ID " + note.getUser().getId() + " does not exist.");
            return false; // User does not exist in DB
        }

        // Set user and default date
        note.setUser(user);
        if (note.getDate() == null) {
            note.setDate(LocalDateTime.now()); // Set the date if not provided
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(note); // Persist the note to the database
            transaction.commit(); // Commit transaction
            System.out.println("Success: Note added.");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of an error
            }
            System.err.println("Error: Failed to add note. " + e.getMessage());
            e.printStackTrace();
            return false; // Return false if something went wrong
        }
    }

    // Get all notes for a specific user by userId
    public List<NoteDetails> getAllNotes(int userId) {
        try {
            return entityManager.createQuery(
                    "SELECT n FROM NoteDetails n WHERE n.user.id = :userId ORDER BY n.date DESC", NoteDetails.class)
                    .setParameter("userId", userId)
                    .getResultList(); // Return all notes for the given user, ordered by date descending
        } catch (Exception e) {
            System.err.println("Error: Failed to retrieve notes for user " + userId + ". " + e.getMessage());
            e.printStackTrace();
            return null; // Return null if there was an issue fetching the notes
        }
    }

    // Get a specific note by its ID
    public NoteDetails getNoteById(int id) {
        try {
            return entityManager.find(NoteDetails.class, id); // Find and return note by its ID
        } catch (Exception e) {
            System.err.println("Error: Failed to retrieve note with ID " + id + ". " + e.getMessage());
            e.printStackTrace();
            return null; // Return null if note was not found
        }
    }

    // Edit an existing note
    public boolean editNote(NoteDetails note) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(note); // Merge the updated note into the database
            transaction.commit(); // Commit the transaction
            return true; // Return true if the edit was successful
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of an error
            }
            System.err.println("Error: Failed to edit note. " + e.getMessage());
            e.printStackTrace();
            return false; // Return false if something went wrong
        }
    }

    // Delete a note by its ID
    public boolean deleteNote(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            NoteDetails note = entityManager.find(NoteDetails.class, id);
            if (note != null) {
                entityManager.remove(note); // Remove the note from the database
                transaction.commit(); // Commit the transaction
                return true; // Return true if the note was deleted
            }
            transaction.commit(); // Commit even if the note doesn't exist
            return false; // Return false if note not found
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of an error
            }
            System.err.println("Error: Failed to delete note. " + e.getMessage());
            e.printStackTrace();
            return false; // Return false if something went wrong
        }
    }
}
