<%@ page import="repositories.CategoriesRepository" %>
<%@ page import="models.Category" %>
<%@ page import="java.util.Optional" %>
<%@ page import="repositories.BooksRepository" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Benjamin
  Date: 1/7/2018
  Time: 10:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Add New Book</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/add-book-page.css" rel="stylesheet">
</head>

<body>

<link href="css/nav.css" rel="stylesheet">
<nav>
    <a href="index.jsp">Home</a>
    <a href="search.jsp">Search</a>
    <a href="return-book.jsp">Return a book</a>
    <a href="register-student.jsp">Register new student</a>
    <a href="add-book.jsp" class="active">Add new book</a>
</nav>

<%
    boolean isPostBack = request.getMethod().equalsIgnoreCase("post");
    Map<String, String> errors = new HashMap<String, String>();

    if (isPostBack) {
        String bookName = request.getParameter("bookName");
        String category = request.getParameter("category");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String year = request.getParameter("year");

        if (bookName == null || bookName.equals("")) {
            errors.put("bookName", "Book is mandatory.");
        }

        if (category == null || category.equals("")) {
            errors.put("category", "category is mandatory.");
        }

        if (author == null || author.equals("")) {
            errors.put("author", "author is mandatory.");
        }

        if (isbn == null || isbn.equals("")) {
            errors.put("isbn", "isbn is mandatoy.");
        }

        if (year == null || year.equals("")) {
            errors.put("year", "year is mandatoy.");
        }

        if (request.getParameter("copies") == null) {
            errors.put("copies", "copies is mandatoy.");
        }

        Integer copies = null;

        try {
            copies = Integer.decode(request.getParameter("copies").toString());
        } catch (NumberFormatException e) {
            errors.put("copies", "copies must be a number.");
        }

        if (errors.isEmpty()) {
            BooksRepository.getInstance().addBook(bookName, category, isbn, author, year, copies);
        }
    }
%>

<form method="post" action="add-book.jsp">

    <h1>Add New Book</h1>

    <label for="bookName">Book Name: </label>
    <input type="text" name="bookName" id="bookName" placeholder="book name" value="<%= Optional.ofNullable(request.getParameter("bookName")).orElse("") %>">
    <% if (errors.get("bookName") != null)
        out.println("<p class='invalid-field'>" + errors.get("bookName") + "</p>"); %>

    <label for="category">Select category:</label>
    <select name="category" id="category">
        <option selected></option>
        <%
            for (Category category : CategoriesRepository.getInstance().getAll()) {
                out.println("<option>" + category.name + "</option>");
            }
        %>
    </select>
    <% if (errors.get("category") != null)
        out.println("<p class='invalid-field'>" + errors.get("category") + "</p>"); %>

    <label for="author">Author: </label>
    <input type="text" name="author" id="author" placeholder="author" value="<%= Optional.ofNullable(request.getParameter("author")).orElse("") %>">
    <% if (errors.get("author") != null) out.println("<p class='invalid-field'>" + errors.get("author") + "</p>"); %>

    <label for="isbn">ISBN: </label>
    <input type="text" id="isbn" name="isbn" placeholder="book's isbn" value="<%= Optional.ofNullable(request.getParameter("isbn")).orElse("") %>">
    <% if (errors.get("isbn") != null) out.println("<p class='invalid-field'>" + errors.get("isbn") + "</p>"); %>

    <label for="publication">Year of publication: </label>
    <input type="number" min="1900" name="year" max="2018" id="publication" placeholder="publication" value="<%= Optional.ofNullable(request.getParameter("year")).orElse("") %>">
    <% if (errors.get("year") != null) out.println("<p class='invalid-field'>" + errors.get("year") + "</p>"); %>

    <label for="copies">Copies: </label>
    <input type="number" min="0" max="100" name="copies" id="copies" placeholder="number of copies" value="<%= Optional.ofNullable(request.getParameter("copies")).orElse("") %>">
    <% if (errors.get("copies") != null) out.println("<p class='invalid-field'>" + errors.get("copies") + "</p>"); %>

    <input type="submit" value="Submit">

</form>
</body>

</html>
