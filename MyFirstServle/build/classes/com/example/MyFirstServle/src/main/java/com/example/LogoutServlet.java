package com.example; // match your package

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Invalidate the current session
        HttpSession session = request.getSession(false); // get existing session, don’t create new
        if(session != null){
            session.invalidate();
        }
        //Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}