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

@WebServlet("/bookAppointment")
public class BookAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String service  = request.getParameter("service");
        String dentist  = request.getParameter("dentist");
        String date     = request.getParameter("date");
        String time     = request.getParameter("time");

        try {
            String xmlPath = getServletContext().getRealPath("/WEB-INF/appointments.xml");
            File xmlFile = new File(xmlPath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Create <appointment> element
            Element appointment = doc.createElement("appointment");

            Element userElem = doc.createElement("username");
            userElem.appendChild(doc.createTextNode(username));
            appointment.appendChild(userElem);

            Element serviceElem = doc.createElement("service");
            serviceElem.appendChild(doc.createTextNode(service));
            appointment.appendChild(serviceElem);

            Element dentistElem = doc.createElement("dentist");
            dentistElem.appendChild(doc.createTextNode(dentist));
            appointment.appendChild(dentistElem);

            Element dateElem = doc.createElement("date");
            dateElem.appendChild(doc.createTextNode(date));
            appointment.appendChild(dateElem);

            Element timeElem = doc.createElement("time");
            timeElem.appendChild(doc.createTextNode(time));
            appointment.appendChild(timeElem);

            // Append to root
            doc.getDocumentElement().appendChild(appointment);

            // Save back to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to home page
        response.sendRedirect("home.jsp");
    }
}