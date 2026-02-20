//package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class signUpServlet
 */
@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Collecting all form fields
        String role = request.getParameter("role"); 
        String fullName = request.getParameter("fullname"); 
        String email = request.getParameter("email"); 
        String dob = request.getParameter("dob"); 
        String password = request.getParameter("password"); 
        String confirmPassword = request.getParameter("confirmPassword");

        // ⭐ Correct file path to deployed XML file
        String filePath = getServletContext().getRealPath("/WEB-INF/xml/users.xml");

        // 1. Password mismatch 
        if (!password.equals(confirmPassword)) { 
            request.setAttribute("error", "Passwords do not match!"); 
            request.getRequestDispatcher("signUp.jsp").forward(request, response); 
            return; 
        }

        // 2. Checking if email already exists in XML 
        if (UserXMLDatabase.emailExists(getServletContext(), email)) { 
            request.setAttribute("error", "Email already registered!"); 
            request.getRequestDispatcher("signUp.jsp").forward(request, response); 
            return; 
        }

        // 3. Save new user into XML ⭐ NOW USING filePath
        UserXMLDatabase.addUser(role, fullName, email, dob, password, filePath);

        // 4. Store user in session 
        HttpSession session = request.getSession(); 
        session.setAttribute("user", fullName); 
        session.setAttribute("role", role);

        // 5. Redirect to home page 
        response.sendRedirect("home.jsp");
    }
}
