<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Create Account</title>
</head>
<body>
<h1>Create Account</h1>
<form action="CreateAccount" method="post">
  Username: <input type="text" name="username" required><br>
  Password: <input type="password" name="password" required><br>
  <input type="submit" value="Create Account">
</form>
<br>
<a href="login.jsp">Back to Login</a>
</body>
</html>
