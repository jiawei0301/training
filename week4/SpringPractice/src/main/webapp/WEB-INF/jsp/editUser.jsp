<!-- WEB-INF/jsp/editUser.jsp -->
<%@ page import="com.example.springpractice.entity.User" %>
<%
    User u = (User) request.getAttribute("user");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h1>Edit User #<%= u.getId() %></h1>
<form action="<%= ctx %>/users/update" method="post">
    <!-- Include user id as a hidden field -->
    <input type="hidden" name="id" value="<%= u.getId() %>">

    <label for="name">Name:</label>
    <input type="text" name="name" id="name" value="<%= u.getName() %>" required><br><br>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" value="<%= u.getEmail() %>" required><br><br>

    <button type="submit">Update</button>
</form>
<p>
    <a href="<%= ctx %>/users/view/<%= u.getId() %>">Cancel</a>
</p>
</body>
</html>
