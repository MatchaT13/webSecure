//package controller;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.servlet.ServletContext;
import java.io.File;

public class UserXMLDatabase {

    // Get the absolute path to users.xml inside WEB-INF/xml
    private static File getXMLFile(ServletContext context) {
        String path = context.getRealPath("/WEB-INF/xml/users.xml");
        return new File(path);
    }

    // Add a new user to users.xml
    public static void addUser(ServletContext context, String role, String fullName,
                               String email, String dob, String password) {
        try {
            File xmlFile = getXMLFile(context);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            Element root = doc.getDocumentElement();

            // Create <user> element
            Element user = doc.createElement("user");
            user.setAttribute("email", email);

            Element nameEl = doc.createElement("fullname");
            nameEl.setTextContent(fullName);

            Element roleEl = doc.createElement("role");
            roleEl.setTextContent(role);

            Element dobEl = doc.createElement("dob");
            dobEl.setTextContent(dob);

            Element passEl = doc.createElement("password");
            passEl.setTextContent(password);

            // Build structure
            user.appendChild(nameEl);
            user.appendChild(roleEl);
            user.appendChild(dobEl);
            user.appendChild(passEl);

            root.appendChild(user);

            // Save XML back to file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check if an email already exists in users.xml
    public static boolean emailExists(ServletContext context, String email) {
        try {
            File xmlFile = getXMLFile(context);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                if (user.getAttribute("email").equalsIgnoreCase(email)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Validate login: return full name if credentials match, else null
    public static String validateLogin(ServletContext context, String email, String password) {
        try {
            File xmlFile = getXMLFile(context);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);

                if (user.getAttribute("email").equalsIgnoreCase(email)) {
                    String storedPass = user.getElementsByTagName("password").item(0).getTextContent();

                    if (storedPass.equals(password)) {
                        return user.getElementsByTagName("fullname").item(0).getTextContent();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
