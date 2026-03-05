package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;


public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	    //️Get login input from user
	    String userId = request.getParameter("userid");  // <-- declare userId
	    String password = request.getParameter("password"); // <-- declare password

	    //️Prepare variables for user info from XML
	    String fullname = "";  // <-- declare fullname
	    String dob = "";       // <-- declare date of birth
	    String email = "";     // <-- declare email
	    String role = "";      // <-- declare role
	    boolean validUser = false;

	    //Check users.xml
	    try {
	        String path = getServletContext().getRealPath("/WEB-INF/users.xml");
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(path);
	        doc.getDocumentElement().normalize();

	        NodeList users = doc.getElementsByTagName("user");
	        for(int i=0;i<users.getLength();i++){
	            Element u = (Element) users.item(i);
	            String uid = u.getElementsByTagName("userid").item(0).getTextContent();
	            String pwd = u.getElementsByTagName("password").item(0).getTextContent();
	            if(uid.equals(userId) && pwd.equals(password)){
	                // Save info to variables
	                fullname = u.getElementsByTagName("fullname").item(0).getTextContent();
	                dob = u.getElementsByTagName("dob").item(0).getTextContent();
	                email = u.getElementsByTagName("email").item(0).getTextContent();
	                role = u.getElementsByTagName("role").item(0).getTextContent();
	                validUser = true;
	                break;
	            }
	        }
	    } catch(Exception e){ e.printStackTrace(); }

	    // If login is valid, store info in session
	    if(validUser){
	        HttpSession session = request.getSession();
	        session.setAttribute("username", userId);
	        session.setAttribute("fullname", fullname);
	        session.setAttribute("dob", dob);
	        session.setAttribute("email", email);
	        session.setAttribute("role", role);

	        response.sendRedirect("home.jsp");
	    } else {
	        request.setAttribute("error", "Invalid username or password");
	        request.getRequestDispatcher("login.html").forward(request, response);
	    }
	    HttpSession session = request.getSession(true);

	 // Force JSESSIONID to be a session cookie
	 Cookie jsession = new Cookie("JSESSIONID", session.getId());
	 jsession.setHttpOnly(true);
	 jsession.setPath(request.getContextPath());
	 jsession.setMaxAge(-1);   // IMPORTANT: delete cookie when browser closes
	 response.addCookie(jsession);
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}

