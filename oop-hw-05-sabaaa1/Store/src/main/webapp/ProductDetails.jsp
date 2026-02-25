<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Products" %>
<%@ page import="DAO.ProductsDAO" %>

<%
  String productId = request.getParameter("id");
  Products product = ProductsDAO.getProductById(productId);
%>
<!DOCTYPE html>
<html>
<head>
  <title><%= product != null ? product.getName() : "Product Not Found" %></title>
</head>
<body>
<% if (product == null) { %>
<h1>Product not found</h1>
<% } else { %>
<h1><%= product.getName() %></h1>
<img src="store-images/<%= product.getImageFile() %>" alt="<%= product.getName() %>" width="200">
<p>$<%= String.format("%.2f", product.getPrice()) %></p>
<form method="post" action="cart">
  <input type="hidden" name="action" value="add">
  <input type="hidden" name="productID" value="<%= product.getProductId() %>">
  <input type="submit" value="Add to Cart">
</form>
<p><a href="index.jsp">Back to Store</a></p>
<% } %>

</body>
</html>
