<!-- WEB-INF/jsp/viewUser.jsp -->
<%@ page import="com.example.entity.User" %>
<%
  User u = (User)request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>View User</title></head>
<body>
<h1>User #<%=u.getId()%></h1>
<p><strong>Name:</strong>  <%=u.getName()%></p>
<p><strong>Email:</strong> <%=u.getEmail()%></p>
<p>
  <a href="<%=request.getContextPath()%>/users/update-user/<%=u.getId()%>">Edit</a>
<form action="<%=request.getContextPath()%>/users/delete-user/<%=u.getId()%>" method="post" style="display:inline">
  <button type="submit">Delete</button>
</form>
</p>
<p><a href="${pageContext.request.contextPath}/users">Back to list</a></p>
</body>
</html>