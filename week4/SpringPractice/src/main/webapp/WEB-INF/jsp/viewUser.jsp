<!-- WEB-INF/jsp/viewUser.jsp -->
<%@ page import="com.example.springpractice.entity.User" %>
<%
    User u = (User) request.getAttribute("user");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>View User</title>
</head>
<body>
<h1>User #<%= u.getId() %></h1>
<p><strong>Name:</strong>  <%= u.getName() %></p>
<p><strong>Email:</strong> <%= u.getEmail() %></p>
<p>
    <a href="<%= ctx %>/users/edit/<%= u.getId() %>">Edit</a>
<form action="<%= ctx %>/users/delete/<%= u.getId() %>" method="post" style="display:inline">
    <button type="submit">Delete</button>
</form>
</p>
<p>
    <a href="<%= ctx %>/users">Back to list</a>
</p>
</body>
</html>
