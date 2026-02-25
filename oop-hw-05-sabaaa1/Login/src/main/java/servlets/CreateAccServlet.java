package servlets;
import manager.AccManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/CreateAccount")
public class CreateAccServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        AccManager accManager = (AccManager) context.getAttribute("accManager");
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        request.setAttribute("username", username);
        if(accManager.addAcc(username, pass)){
            request.getRequestDispatcher("/newProfile.jsp").forward(request, response);
        }
        else{
            request.getRequestDispatcher("/usedName.jsp").forward(request, response);
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("createAccount.jsp");
    }
}
