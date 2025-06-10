<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Form</title>
</head>
<body>
    <h2>${user.id == 0 ? 'Add' : 'Edit'} User</h2>
    <form action="${user.id == 0 ? pageContext.request.contextPath + '/users/save' : pageContext.request.contextPath + '/users/update'}" method="post">
        <input type="hidden" name="id" value="${user.id}" />
        Name: <input type="text" name="name" value="${user.name}" required /><br/>
        Email: <input type="email" name="email" value="${user.email}" required /><br/>
        <button type="submit">${user.id == 0 ? 'Create' : 'Update'}</button>
    </form>
    <a href="${pageContext.request.contextPath}/users">Back to List</a>
</body>
</html>