<!-- WEB-INF/jsp/editUser.jsp -->
<%@ page import="com.example.entity.User" %>
<%
    User u = (User)request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>Edit User</title></head>
<body>
<h1>Edit User #<%=u.getId()%></h1>
<form action="${pageContext.request.contextPath}/users/update-user/<%=u.getId()%>" method="post">
    Name:  <input name="name"  value="<%=u.getName()%>"  required/><br/><br/>
    Email: <input name="email" value="<%=u.getEmail()%>" required/><br/><br/>
    <button type="submit">Update</button>
</form>
<p><a href="${pageContext.request.contextPath}/users/view-user/<%=u.getId()%>">Cancel</a></p>
</body>
</html>