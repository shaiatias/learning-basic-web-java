/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helpers.BeanClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Book;
import repositories.BooksRepository;
import repositories.CategoriesRepository;

@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class Search extends HttpServlet {

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

        BeanClass beanClass = new BeanClass();
        beanClass.searchResults = null;

        request.getSession().setAttribute("results", beanClass);
        request.setAttribute("optionsString", getAllCategoriesAsOptions());
        request.getRequestDispatcher("search.jsp").forward(request, response);
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
        
        String bookName = request.getParameter("bookName");
        String category = request.getParameter("category");
        
        BeanClass beanClass = new BeanClass();
        beanClass.searchResults = getSearchResults(bookName, category);

        request.getSession().setAttribute("results", beanClass);
        request.setAttribute("optionsString", getAllCategoriesAsOptions());
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    private List<Book> getSearchResults( String title, String category) {

        if (title == null) {
            title = "*";
        }

        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("categoty is missing");
        }

        return BooksRepository.getInstance().findBookByName(title)
                .stream()
                .filter((t) -> {
                    return category.equalsIgnoreCase("all") || t.category.equalsIgnoreCase(category);
                }).collect(Collectors.toList());
    }

    private String getAllCategoriesAsOptions() {

        return CategoriesRepository.getInstance().getAll().stream().map((t) -> {
            return "<option>" + t.name + "</option>";
        }).collect(Collectors.joining());
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
