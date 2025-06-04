package com.example.servlet;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

// Using annotation; if using web.xml, remove @WebServlet
//@WebServlet(urlPatterns = "/users/*")
public class UserServlet extends HttpServlet {
    // Database credentials & URL
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    //private static final String USER = "postgres";
    //private static final String PASSWORD = "your_password";

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    // Helper: Parse JSON from request body
    private JSONObject parseRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    // Helper: Send JSON response
    private void sendJsonResponse(HttpServletResponse response, JSONObject obj, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        PrintWriter out = response.getWriter();
        out.print(obj.toString());
        out.flush();
    }

    // GET /users        → list all users
    // GET /users/{id}   → get one user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // null, "/", or "/{id}"
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
                // List all users
                String sql = "SELECT id, name, email FROM users";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                JSONArray users = new JSONArray();

                while (rs.next()) {
                    JSONObject user = new JSONObject();
                    user.put("id", rs.getInt("id"));
                    user.put("name", rs.getString("name"));
                    user.put("email", rs.getString("email"));
                    users.put(user);
                }
                JSONObject result = new JSONObject();
                result.put("users", users);
                sendJsonResponse(response, result, HttpServletResponse.SC_OK);
            } else {
                // Retrieve one user by ID
                String[] parts = pathInfo.split("/");
                if (parts.length != 2) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
                    return;
                }
                int id = Integer.parseInt(parts[1]);
                String sql = "SELECT id, name, email FROM users WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    JSONObject user = new JSONObject();
                    user.put("id", rs.getInt("id"));
                    user.put("name", rs.getString("name"));
                    user.put("email", rs.getString("email"));
                    sendJsonResponse(response, user, HttpServletResponse.SC_OK);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

    // POST /users → create new user
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject body = parseRequestBody(request);
        String name = body.optString("name");
        String email = body.optString("email");

        if (name.isEmpty() || email.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name and email are required");
            return;
        }

        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO users (name, email) VALUES (?, ?) RETURNING id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int newId = rs.getInt("id");
                JSONObject created = new JSONObject();
                created.put("id", newId);
                created.put("name", name);
                created.put("email", email);
                sendJsonResponse(response, created, HttpServletResponse.SC_CREATED);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create user");
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

    // PUT /users/{id} → update existing user
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // expect "/{id}"
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required in URL");
            return;
        }
        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
            return;
        }
        int id = Integer.parseInt(parts[1]);
        JSONObject body = parseRequestBody(request);
        String name = body.optString("name");
        String email = body.optString("email");

        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id);
            int affected = ps.executeUpdate();

            if (affected > 0) {
                JSONObject updated = new JSONObject();
                updated.put("id", id);
                updated.put("name", name);
                updated.put("email", email);
                sendJsonResponse(response, updated, HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }

    // DELETE /users/{id} → delete user
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // expect "/{id}"
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required in URL");
            return;
        }
        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
            return;
        }
        int id = Integer.parseInt(parts[1]);

        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int affected = ps.executeUpdate();

            if (affected > 0) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }
}