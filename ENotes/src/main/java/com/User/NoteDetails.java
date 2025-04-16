package com.User;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "note")
public class NoteDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Timestamp date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private UserDetails user;

    public NoteDetails() {
        // Automatically set the current date/time when creating a new note
        this.date = Timestamp.valueOf(LocalDateTime.now());
    }

    public NoteDetails(String title, String content, UserDetails user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int noteId) {
        this.id = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date.toLocalDateTime();
    }

    public void setDate(LocalDateTime date) {
        this.date = Timestamp.valueOf(date);
    }

    public String getDateFormatted() {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.toLocalDateTime().format(formatter);
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "NoteDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + getDateFormatted() +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}
