package servlets;

import util.Cart;
import java.io.*;
import java.util.HashSet;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/cart")
public class ServletCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("MyCart.jsp");


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();


            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("nextMove");
        if (action == null) {
            action = request.getParameter("action");
        }

        if ("add".equalsIgnoreCase(action)) {
            String id = request.getParameter("productID");
            if (id != null && !id.trim().isEmpty()) {


                cart.addProduct(id);
            }
        } else if ("update".equalsIgnoreCase(action)) {
            for (String id : new HashSet<>(cart.getIds()))  {

                String quantityStr = request.getParameter("quantity_" + id);
                if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                    try {
                        int quantity= Integer.parseInt(quantityStr.trim());
                        cart.updateQuantity(id, quantity);


                    } catch (NumberFormatException e) {
                    }
                }
            }
        } else if ("clear".equalsIgnoreCase(action)) {
            cart.deleteAll();
        }


        response.sendRedirect("MyCart.jsp");
    }
}
