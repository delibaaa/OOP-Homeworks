package servlets;
import manager.AccManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        AccManager accManager = (AccManager) context.getAttribute("accManager");
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        if(accManager.accExist(username, pass)){
            request.setAttribute("username", username);
            request.getRequestDispatcher("/userProfile.jsp").forward(request, response);
        }
        else{
            request.setAttribute("error", "Incorrect username or password, try again");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }
}
