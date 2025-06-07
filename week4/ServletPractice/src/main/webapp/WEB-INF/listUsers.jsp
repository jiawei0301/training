<!-- WEB-INF/jsp/listUsers.jsp -->
<%@ page import="java.util.List, com.example.entity.User" %>
<%
    List<User> users = (List<User>)request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head><title>All Users</title></head>
<body>
<h1>Users</h1>
<a href="${pageContext.request.contextPath}/users/new-user">+ New User</a>
<table border="1" cellpadding="5">
    <tr><th>ID</th><th>Name</th><th>Email</th><th>Actions</th></tr>
    <%
        for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/users/view-user/<%=u.getId()%>">
                <%=u.getName()%>
            </a>
        </td>
        <td><%=u.getEmail()%></td>
        <td>
            <a href="<%=request.getContextPath()%>/users/update-user/<%=u.getId()%>">Edit</a>
            <form action="<%=request.getContextPath()%>/users/delete-user/<%=u.getId()%>"
                  method="post" style="display:inline">
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>