<!-- WEB-INF/jsp/newUser.jsp -->
<!DOCTYPE html>
<html>
<head><title>Create User</title></head>
<body>
<h1>New User</h1>
<form action="${pageContext.request.contextPath}/users/new-user" method="post">
  Name:  <input name="name"  required/><br/><br/>
  Email: <input name="email" required/><br/><br/>
  <button type="submit">Create</button>
</form>
<p><a href="${pageContext.request.contextPath}/users">Back to list</a></p>
</body>
</html>