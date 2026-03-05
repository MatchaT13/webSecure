package com.example;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

@WebServlet("/signUpServlet")
public class signUpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        String fullname = request.getParameter("fullname");
        String userid = request.getParameter("userid");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        //Check password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
            return;
        }

        try {

            String path = getServletContext().getRealPath("/WEB-INF/users.xml");
            File xmlFile = new File(path);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList users = doc.getElementsByTagName("user");

            //Check for duplicate username
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                String existingUser = user.getElementsByTagName("userid")
                        .item(0).getTextContent();

                if (existingUser.equals(userid)) {
                    request.setAttribute("error", "Username already exists!");
                    request.getRequestDispatcher("signUp.jsp").forward(request, response);
                    return;
                }
            }
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                String existingUser = user.getElementsByTagName("email")
                        .item(0).getTextContent();

                if (existingUser.equals(email)) {
                    request.setAttribute("error", "Email already exists!");
                    request.getRequestDispatcher("signUp.jsp").forward(request, response);
                    return;
                }
            }

            //Create new user node
            Element newUser = doc.createElement("user");

            Element xmlRole = doc.createElement("role");
            xmlRole.appendChild(doc.createTextNode(role));

            Element xmlFullname = doc.createElement("fullname");
            xmlFullname.appendChild(doc.createTextNode(fullname));

            Element xmlUserid = doc.createElement("userid");
            xmlUserid.appendChild(doc.createTextNode(userid));

            Element xmlEmail = doc.createElement("email");
            xmlEmail.appendChild(doc.createTextNode(email));

            Element xmlDob = doc.createElement("dob");
            xmlDob.appendChild(doc.createTextNode(dob));

            Element xmlPassword = doc.createElement("password");
            xmlPassword.appendChild(doc.createTextNode(password));

            newUser.appendChild(xmlRole);
            newUser.appendChild(xmlFullname);
            newUser.appendChild(xmlUserid);
            newUser.appendChild(xmlEmail);
            newUser.appendChild(xmlDob);
            newUser.appendChild(xmlPassword);

            doc.getDocumentElement().appendChild(newUser);

            //Save XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);

            transformer.transform(source, result);

            //Redirect to login page
            response.sendRedirect("login.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("signUp.jsp");
    }
}
