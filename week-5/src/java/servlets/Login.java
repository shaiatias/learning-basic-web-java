/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helpers.BeanClass;
import helpers.LoginHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author shai
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String error)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect("welcome.html");
            return;
        }

        Optional<Cookie> fromCookies = Arrays.asList(request.getCookies()).stream().filter(c -> c.getName().equals("username")).findFirst();
        Optional<String> fromRequest = Optional.ofNullable(request.getParameter("username"));

        String username = fromRequest.orElse(fromCookies.map(c -> c.getValue()).orElse(""));
        request.setAttribute("username", username);
        request.getSession().setAttribute("error1", error);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> errors = new HashMap();
        HttpSession session = request.getSession();

        boolean isLoggedIn = session.getAttribute("username") != null;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.equals("")) {
            errors.put("username", "username is mandatory.");
        }

        if (password == null || password.equals("")) {
            errors.put("password", "password is mandatory.");
        }

        if (errors.isEmpty()) {

            User authorized = LoginHelper.isAuthorized(username, password);

            if (authorized != null) {
                response.addCookie(new Cookie("username", authorized.username));

                session.setAttribute("role", authorized.role);
                session.setAttribute("username", username);
                session.setAttribute("studentId", authorized.studentId);
                session.setAttribute("lastLoginTime", authorized.lastLoginTime);
                response.sendRedirect("welcome.jsp");
            } else {
                errors.put("general", "bad credentials");
            }
        } else {
            processRequest(request, response, errors.values().stream().findFirst().orElse(""));
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
