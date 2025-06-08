<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="java.util.List, com.example.springpractice.entity.User" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head><title>All Users</title></head>
<body>
<h1>Users</h1>
<!-- Fixed path: GET /users/new -->
<a href="<%= request.getContextPath() %>/users/new">+ New User</a>
<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Name</th><th>Email</th><th>Actions</th></tr>
    <%
        for (User u : users) {
    %>
    <tr>
        <td><%= u.getId() %></td>
        <td>
            <!-- Fixed view URL: GET /users/view/{id} -->
            <a href="<%= request.getContextPath() %>/users/view/<%= u.getId() %>">
                <%= u.getName() %>
            </a>
        </td>
        <td><%= u.getEmail() %></td>
        <td>
            <!-- Fixed edit URL: GET /users/edit/{id} -->
            <a href="<%= request.getContextPath() %>/users/edit/<%= u.getId() %>">Edit</a>
            <!-- Fixed delete URL: POST /users/delete/{id} -->
            <form action="<%= request.getContextPath() %>/users/delete/<%= u.getId() %>" method="post" style="display:inline">
                <button type="submit" onclick="return confirm('Delete user?');">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>