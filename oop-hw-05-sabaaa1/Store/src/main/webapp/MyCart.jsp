<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.Cart" %>
<%@ page import="util.Products" %>
<%@ page import="java.util.Set" %>
<%@ page import="DAO.ProductsDAO" %>

<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h1>Shopping Cart</h1>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
%>
<p>Your shopping cart is empty.</p>
<p><a href="index.jsp">Continue Shopping</a></p>
<%
} else {
    Set<String> productIds = cart.getIds();
    double totalPrice = 0.0;
%>

<form method="post" action="cart">
    <table>
        <tr>
            <th>Qty</th>
            <th>Item</th>
            <th>Price</th>
        </tr>
        <%for (String id : productIds) {
                Products product =ProductsDAO.getProductById(id);
                if (product!=null && cart.getQuantity(id) > 0) {
                    int qty = cart.getQuantity(id);
                    double price= product.getPrice() * qty;
                    totalPrice += price;%>
        <tr>
            <td><input type="number" name="quantity_<%= id %>" value="<%= qty %>" min="0"></td>
            <td><%= product.getName() %></td>
            <td>$<%= String.format("%.2f", price) %></td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <p><strong>Total: </strong>$<%= String.format("%.2f", totalPrice) %></p>
    <button type="submit" name="nextMove" value="update">Update Cart</button>
    <button type="submit" name="nextMove" value="clear">Clear Cart</button>
</form>
<p><a href="index.jsp">Continue Shopping</a></p>
<%
    }
%>
</body>
</html>
