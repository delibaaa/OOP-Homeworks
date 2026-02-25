<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Products" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.ProductsDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Store</title>
</head>
<body>
<h1>Student Store</h1>
<p>Items available:</p>
<ul>
    <%List<Products> products = ProductsDAO.getAll();
        for (Products product : products) { %>
    <li>
        <a href="ProductDetails.jsp?id=<%= product.getProductId() %>">
            <%= product.getName() %>
        </a>
    </li>
    <% } %>
</ul>
</body>
</html>
