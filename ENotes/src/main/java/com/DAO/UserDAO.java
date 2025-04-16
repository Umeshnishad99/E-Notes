package com.DAO;

import com.User.UserDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Add a new user to the database
    public static boolean addUser(UserDetails user, Connection conn) throws SQLException {
        UserDetails userFound = UserDAO.findUserByEmail(user, conn);
        if (userFound != null)
            return false;

        String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps.executeUpdate() > 0;
        }
    }

    // Find a user by email and password for login verification
    public static UserDetails findUser(UserDetails user, Connection conn) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    return user;
                }
            }
            return null;
        }
    }

    // Find a user by email
    public static UserDetails findUserByEmail(UserDetails user, Connection conn) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?"; // Fixed table name
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Properly populate the user object
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
            return null;
        }
    }
}
