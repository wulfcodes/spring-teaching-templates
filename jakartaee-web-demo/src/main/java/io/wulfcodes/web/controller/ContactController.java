package io.wulfcodes.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContactController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name != null && !name.isEmpty()) {
            req.setAttribute("name", name);
        }
        req.getRequestDispatcher("/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String message = req.getParameter("message");

        System.out.println("Received contact request from: " + name + " (" + email + ")");
        System.out.println("Message: " + message);

        resp.setContentType("text/html");
        resp.getWriter().write("<html><body><h1>Thank you " + name
                + "</h1><p>We have received your message.</p><a href='contact'>Go back</a></body></html>");
    }
}
