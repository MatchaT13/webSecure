package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.File;


public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String userInput = request.getParameter("userid");
	    String passInput = request.getParameter("password");

	    boolean validUser = false;

	    try {

	        // Get real path to users.xml inside WEB-INF
	        String path = getServletContext().getRealPath("/WEB-INF/users.xml");
	        File xmlFile = new File(path);

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(xmlFile);

	        doc.getDocumentElement().normalize();

	        NodeList userList = doc.getElementsByTagName("user");

	        for (int i = 0; i < userList.getLength(); i++) {

	            Node node = userList.item(i);

	            if (node.getNodeType() == Node.ELEMENT_NODE) {

	                Element element = (Element) node;

	                String userid = element.getElementsByTagName("userid")
	                        .item(0).getTextContent();

	                String password = element.getElementsByTagName("password")
	                        .item(0).getTextContent();

	                if (userid.equals(userInput) && password.equals(passInput)) {
	                    validUser = true;
	                    break;
	                }
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    if (validUser) {

	        HttpSession session = request.getSession();
	        session.setAttribute("username", userInput);

	        response.sendRedirect("home.jsp");

	    } else {

	        response.sendRedirect("login.html");
	    }
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}

