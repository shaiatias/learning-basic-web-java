/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helpers.BeanClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Book;
import repositories.BookReservationsRepository;
import repositories.BooksRepository;
import repositories.CategoriesRepository;

/**
 *
 * @author shai
 */
@WebServlet(name = "AddReservation", urlPatterns = {"/AddReservation"})
public class AddReservation extends HttpServlet {

    private String getAllCategoriesAsOptions() {

        return CategoriesRepository.getInstance().getAll().stream().map((t) -> {
            return "<option>" + t.name + "</option>";
        }).collect(Collectors.joining());
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
        request.setAttribute("categoriesOptions", getAllCategoriesAsOptions());
        request.getRequestDispatcher("addReservation.jsp").forward(request, response);
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

        try {

            String categoty = (String) request.getParameter("categoty");
            String bookName = (String) request.getParameter("bookName");

            // select category, show books list
            if (bookName == null) {
                List<Book> findBooksByCategory = BooksRepository.getInstance().findBooksByCategory(categoty);
                BeanClass bc = new BeanClass();
                bc.searchResults = findBooksByCategory;

                request.setAttribute("results", bc);
                request.setAttribute("categoriesOptions", getAllCategoriesAsOptions());

                request.getRequestDispatcher("addReservation.jsp").forward(request, response);
            } 

            // selected both, do the reservation
            else {

                String studentId = (String) request.getSession().getAttribute("studentId");

                if (studentId == null) {
                    throw new RuntimeException("student id is not found in the session");
                }
                
                BookReservationsRepository.getInstance().reserveBook(studentId, bookName);

                request.setAttribute("message", "Reservation is saved successfully");
                request.getRequestDispatcher("success.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
