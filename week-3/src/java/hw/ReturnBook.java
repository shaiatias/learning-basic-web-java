/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw;

import static hw.Constants.MAX_RESERVATION_IN_MILLIS;
import static hw.Constants.PANLETY_IN_BITCOIN;
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
import models.BookReservation;
import repositories.BookReservationsRepository;

/**
 *
 * @author shai
 */
@WebServlet(name = "ReturnBook", urlPatterns = {"/ReturnBook"})
public class ReturnBook extends HttpServlet {

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

            String bookId = showValue ? request.getParameter("bookId") : null;
            String studentId = showValue ? request.getParameter("studentId") : null;

            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n"
                    + "    <title>Return a book</title>\n"
                    + "    <link rel=\"stylesheet\" href=\"css/return-book-page.css\">\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "\n"
                    + "    <link href=\"css/nav.css\" rel=\"stylesheet\">\n"
                    + "    <nav>\n"
                    + "        <a href=\"/\">Home</a>\n"
                    + "        <a href=\"/SearchBook\">Search</a>\n"
                    + "        <a href=\"/ReturnBook\" class=\"active\">Return a book</a>\n"
                    + "        <a href=\"/RegisterStudent\">Register new student</a>\n"
                    + "        <a href=\"/AddBook\">Add new book</a>\n"
                    + "    </nav>\n"
                    + "\n"
                    + "    <form>\n"
                    + "\n"
                    + "        <h1>Return a book</h1>\n"
                    + "\n"
                    + "        <label for=\"bookId\">Book Id:</label>\n"
                    + "        <input type=\"text\" name=\"bookId\" value=\"" + Optional.ofNullable(bookId).orElse("") + "\" id=\"bookId\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("bookId")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"studentId\">Student Id:</label>\n"
                    + "        <input type=\"text\" name=\"studentId\" value=\"" + Optional.ofNullable(studentId).orElse("") + "\" id=\"studentId\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("studentId")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <input type=\"submit\" value=\"Return Book\">\n"
                    + "\n"
                    + "    </form>\n"
                    + "\n"
                    + "    " + (isSuccess ? "<h3>book returned successfully</h3>" : "") + "\n"
                    + "\n"
                    + "    <h3>" + Optional.ofNullable(errors.get("general")).orElse("") + "</h3>\n"
                    + "\n"
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

        String bookId = request.getParameter("bookId");
        String studentId = request.getParameter("studentId");

        if (request.getParameter("bookName") == null || request.getParameter("bookName").isEmpty()) {
            errors.put("bookName", "name is mandatory");
        }

        if (request.getParameter("category") == null || request.getParameter("category").isEmpty()) {
            errors.put("category", "category is mandatory");
        }

        if (errors.isEmpty()) {
            Optional<BookReservation> reservation = BookReservationsRepository.getInstance().returnBook(bookId, studentId);

            if (!reservation.isPresent()) {
                errors.put("general", "reservation not found");
            } else {
                BookReservation bookReservation = reservation.get();

                if (bookReservation.from + MAX_RESERVATION_IN_MILLIS < System.currentTimeMillis()) {
                    errors.put("general", "user needs to be punished, take " + PANLETY_IN_BITCOIN + " btc from him!");
                }
            }
        }
    }
}
