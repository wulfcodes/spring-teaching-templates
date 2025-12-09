package io.wulfcodes.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            writer.println("<h1>Hello World!</h1>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
