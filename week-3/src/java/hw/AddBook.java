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
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import repositories.BooksRepository;
import repositories.CategoriesRepository;

/**
 *
 * @author shai
 */
@WebServlet(name = "AddBook", urlPatterns = {"/AddBook"})
public class AddBook extends HttpServlet {

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
            
            String bookName = showValue ? request.getParameter("bookName") : null;
            String author = showValue ? request.getParameter("author") : null;
            String isbn = showValue ? request.getParameter("isbn") : null;
            String publication = showValue ? request.getParameter("publication") : null;
            String copies = showValue ? request.getParameter("copies") : null;

            String options = getAllCategoriesAsOptions();

            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "\n"
                    + "<head>\n"
                    + "    <title>Add New Book</title>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <link href=\"css/add-book-page.css\" rel=\"stylesheet\">\n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "\n"
                    + "    <link href=\"css/nav.css\" rel=\"stylesheet\">\n"
                    + "    <nav>\n"
                    + "        <a href=\"/\">Home</a>\n"
                    + "        <a href=\"/SearchBook\">Search</a>\n"
                    + "        <a href=\"/ReturnBook\">Return a book</a>\n"
                    + "        <a href=\"/RegisterStudent\">Register new student</a>\n"
                    + "        <a href=\"/AddBook\" class=\"active\">Add new book</a>\n"
                    + "    </nav>\n"
                    + "\n"
                    + "    <form action='AddBook' method='POST' >\n"
                    + "\n"
                    + "        <h1>Add New Book</h1>\n"
                    + "\n"
                    + "        <label for=\"bookName\">Book Name: </label>\n"
                    + "        <input type=\"text\" name=\"bookName\" value=\"" + Optional.ofNullable(bookName).orElse("") + "\" id=\"bookName\" placeholder=\"book name\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("bookName")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"category\">Select category:</label>\n"
                    + "        <select name=\"category\" id=\"category\">\n"
                    + "            " + options
                    + "        </select>\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("category")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"author\">Author: </label>\n"
                    + "        <input type=\"text\" name=\"author\" value=\"" + Optional.ofNullable(author).orElse("") + "\" id=\"author\" placeholder=\"author\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("author")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"isbn\">ISBN: </label>\n"
                    + "        <input type=\"text\" name=\"isbn\" value=\"" + Optional.ofNullable(isbn).orElse("") + "\" id=\"isbn\" placeholder=\"book's isbn\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("isbn")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"publication\">Year of publication: </label>\n"
                    + "        <input type=\"number\" name=\"publication\" value=\"" + Optional.ofNullable(publication).orElse("") + "\" min=\"1900\" max=\"2018\" id=\"publication\" placeholder=\"publication\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("publication")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <label for=\"copies\">Copies: </label>\n"
                    + "        <input type=\"number\" name=\"copies\" value=\"" + Optional.ofNullable(copies).orElse("") + "\" min=\"0\" max=\"100\" id=\"copies\" placeholder=\"number of copies\">\n"
                    + "        <span class=\"invalid-field\">" + Optional.ofNullable(errors.get("copies")).orElse("") + "</span>\n"
                    + "\n"
                    + "        <input type=\"submit\" value=\"Submit\">\n"
                    + "\n"
                    + "    </form>\n"
                    + "\n"
                    + "    " + (isSuccess ? "<h3>book added successfully</h3>" : "") + "\n"
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

        String bookName = request.getParameter("bookName");
        String category = request.getParameter("category");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String publication = request.getParameter("publication");
        String copies = request.getParameter("copies");
        Integer nCopies = null;

        if (request.getParameter("bookName") == null || request.getParameter("bookName").isEmpty()) {
            errors.put("bookName", "name is mandatory");
        }

        if (request.getParameter("category") == null || request.getParameter("category").isEmpty()) {
            errors.put("category", "category is mandatory");
        }

        if (request.getParameter("author") == null || request.getParameter("author").isEmpty()) {
            errors.put("author", "author is mandatory");
        }

        if (request.getParameter("isbn") == null || request.getParameter("isbn").isEmpty()) {
            errors.put("isbn", "isbn is mandatory");
        }

        if (request.getParameter("publication") == null || request.getParameter("publication").isEmpty()) {
            errors.put("publication", "publication is mandatory");
        }

        if (request.getParameter("copies") == null || request.getParameter("copies").isEmpty()) {
            errors.put("copies", "copies is mandatory");
        }

        if (request.getParameter("copies") != null) {

            try {
                nCopies = Integer.parseInt(request.getParameter("copies"));
            } catch (NumberFormatException e) {
                errors.put("copies", "copies must be a number");
            }
        }

        if (errors.isEmpty()) {
            BooksRepository.getInstance().addBook(bookName, category, isbn, author, publication, nCopies);
        }
    }

    private String getAllCategoriesAsOptions() {

        return CategoriesRepository.getInstance().getAll().stream().map((t) -> {
            return "<option>" + t.name + "</option>";
        }).collect(Collectors.joining());
    }
}
