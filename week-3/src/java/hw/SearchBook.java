/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Book;
import repositories.BooksRepository;
import repositories.CategoriesRepository;

/**
 *
 * @author shai
 */
@WebServlet(name = "SearchBook", urlPatterns = {"/SearchBook"})
public class SearchBook extends HttpServlet {

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
            
            String options = getAllCategoriesAsOptions();
            
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "\n"
                    + "<head>\n"
                    + "    <title>Serach for books</title>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                    + "    <link href=\"css/search-book-page.css\" rel=\"stylesheet\">\n"
                    + "    \n"
                    + "</head>\n"
                    + "\n"
                    + "<body>\n"
                    + "\n"
                    + "    <link href=\"css/nav.css\" rel=\"stylesheet\">\n"
                    + "    <nav>\n"
                    + "        <a href=\"/\">Home</a>\n"
                    + "        <a href=\"/SearchBook\" class=\"active\">Search</a>\n"
                    + "        <a href=\"/ReturnBook\">Return a book</a>\n"
                    + "        <a href=\"/RegisterStudent\">Register new student</a>\n"
                    + "        <a href=\"/AddBook\">Add new book</a>\n"
                    + "    </nav>\n"
                    + "\n"
                    + "    <form action='SearchBook' method='POST' >\n"
                    + "\n"
                    + "        <h1>Serach for books</h1>\n"
                    + "\n"
                    + "        <label for=\"category\">Select category:</label>\n"
                    + "        <select name=\"category\" id=\"category\">\n"
                    + "            <option value='all' selected>All</option>\n"
                    + "            " + options
                    + "        </select>\n"
                    + "\n"
                    + "        <label for=\"title\">Enter Title: </label>\n"
                    + "        <input type=\"text\" id=\"title\" value=\"" + Optional.ofNullable(request.getParameter("title")).orElse("") + "\" placeholder=\"title\">\n"
                    + "\n"
                    + "        <input type=\"submit\" value=\"Search\">\n"
                    + "\n"
                    + "    </form>\n");
            
            if (request.getMethod().equalsIgnoreCase("post")) {
                printSearchResults(out, request.getParameter("title"), request.getParameter("category"));
            }
            
            out.println(
                    "\n"
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

    private void printSearchResults(PrintWriter out, String title, String category) {
        
        
        if (title == null) {
            title = "*";
        }
        
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("categoty is missing");
        }

        List<Book> books = BooksRepository.getInstance().findBookByName(title)
                .filter((t) -> {
            return category.equalsIgnoreCase("all") || t.category.equalsIgnoreCase(category);
        }).collect(Collectors.toList());
        
        out.println(""
                + "<h1>Found " + books.size() + " Books</h1>"
                + "<ul>");
        
        books.forEach((t) -> {
            out.println("<li>" + t.name + "</li>");
        });
        
        out.println("</ul>");
    }

    private String getAllCategoriesAsOptions() {
        
        return CategoriesRepository.getInstance().getAll().stream().map((t) -> {
            return "<option>" + t.name + "</option>";
        }).collect(Collectors.joining());
    }
}
