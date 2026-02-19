package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 * Servlet implementation class signUpServlet
 */
@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String role= request.getParameter ("role");
		String fullName = request.getParameter ("fullname");
		String email = request.getParameter ("email");
		String dob = request.getParameter ("dob");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		if (!password.equals(confirmPassword)) {
			request.setAttribute("error","Passwords do not match!");
			RequestDispatcher rd = request.getRequestDispatcher ("signUp.jsp");
			rd.forward(request, response);
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute ("user", fullName);
		session.setAttribute("role", role);
		response.sendRedirect("home.jsp");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
}
