<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
<h1><%= error %></h1>
<% } %>
<h1>Login</h1>
<form action="Login" method="post">
  Username: <input type="text" name="username" required><br>
  Password: <input type="password" name="password" required><br>
  <input type="submit" value="Login"> </form>
<br>
<a href="createAccount.jsp">Create a new account</a></body>
</html>