<!-- WEB-INF/jsp/newUser.jsp -->
<%@ page import="com.example.springpractice.entity.User" %>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Create New User</title>
</head>
<body>
<h1>Create New User</h1>
<form action="<%= ctx %>/users" method="post">
    <label for="name">Name:</label>
    <input type="text" name="name" id="name" required><br><br>

    <label for="email">Email:</label>
    <input type="email" name="email" id="email" required><br><br>

    <button type="submit">Create</button>
</form>
<p>
    <a href="<%= ctx %>/users">Back to list</a>
</p>
</body>
</html>
