/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repositories.StudentsRepository;

/**
 *
 * @author shai
 */
@WebServlet(name = "RegisterStudent", urlPatterns = {"/RegisterStudent"})
public class RegisterStudent extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Map<String, String> errors = new HashMap<>();

            if (request.getMethod().equalsIgnoreCase("post")) {
                processPost(request, errors);
            }

            boolean showValue = request.getMethod().equalsIgnoreCase("post") && !errors.isEmpty();
            boolean isSuccess = request.getMethod().equalsIgnoreCase("post") && errors.isEmpty();

            String firstName = showValue ? request.getParameter("firstName") : null;
            String lastName = showValue ? request.getParameter("lastName") : null;
            String studentId = showValue ? request.getParameter("studentId") : null;
            String email = showValue ? request.getParameter("email") : null;

            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "\n"
                    + "<head>\n"
                    + "    <title>Register New Student</title>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <link href=\"css/registration-page.css\" rel=\"stylesheet\">\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "\n"
                    + "    <link href=\"css/nav.css\" rel=\"stylesheet\">\n"
                    + "    <nav>\n"
                    + "        <a href=\"/\">Home</a>\n"
                    + "        <a href=\"/SearchBook\">Search</a>\n"
                    + "        <a href=\"/ReturnBook\">Return a book</a>\n"
                    + "        <a href=\"/RegisterStudent\" class=\"active\">Register new student</a>\n"
                    + "        <a href=\"/AddBook\">Add new book</a>\n"
                    + "    </nav>\n"
                    + "\n"
                    + "    <form action=\"RegisterStudent\" method=\"POST\">\n"
                    + "\n"
                    + "        <h1>Register New Student</h1>\n"
                    + "\n"
                    + "        <label for=\"firstName\">First name: </label>\n"
                    + "        <input type=\"text\" id=\"firstName\" name=\"firstName\" value=\"" + Optional.ofNullable(firstName).orElse("") + "\" placeholder=\"first name\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("firstName")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"lastName\">Last name: </label>\n"
                    + "        <input type=\"text\" id=\"lastName\" name=\"lastName\" value=\"" + Optional.ofNullable(lastName).orElse("") + "\" placeholder=\"last name\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("lastName")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"studentId\">ID: </label>\n"
                    + "        <input type=\"text\" id=\"studentId\" name=\"studentId\" value=\"" + Optional.ofNullable(studentId).orElse("") + "\" placeholder=\"student id\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("studentId")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"email\">Email: </label>\n"
                    + "        <input type=\"email\" id=\"email\" name=\"email\" value=\"" + Optional.ofNullable(email).orElse("") + "\" placeholder=\"email\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("email")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <input type=\"submit\" value=\"Submit\">\n"
                    + "\n"
                    + "    </form>\n"
                    + "\n"
                    + "    " + (isSuccess ? "<h3>student added successfully</h3>" : "") + "\n"
                    + "</body>\n"
                    + "\n"
                    + "</html>");
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private void processPost(HttpServletRequest request, Map<String, String> errors) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String studentId = request.getParameter("studentId");
        String email = request.getParameter("email");

        if (firstName == null || firstName.isEmpty()) {
            errors.put("firstName", "first name is mandatory");
        }

        if (lastName == null || lastName.isEmpty()) {
            errors.put("lastName", "last name is mandatory");
        }

        if (studentId == null || studentId.isEmpty()) {
            errors.put("studentId", "student id is mandatory");
        }

        if (email == null || email.isEmpty()) {
            errors.put("email", "email is mandatory");
        }

        if (errors.isEmpty()) {
            StudentsRepository.getInstance().addStudent(firstName, lastName, studentId, email);
        }
    }
}
