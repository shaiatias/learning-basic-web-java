<%@ page import="models.Book" %>
<%@ page import="repositories.BooksRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="repositories.CategoriesRepository" %>
<%@ page import="models.Category" %>

<%--
  Created by IntelliJ IDEA.
  User: Benjamin
  Date: 1/7/2018
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Serach for books</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/search-book-page.css">
    <link rel="stylesheet" href="css/nav.css">

</head>

<body>

<link href="nav.css" rel="stylesheet">
<nav>
    <a href="index.jsp">Home</a>
    <a href="search-book.jsp" class="active">Search</a>
    <a href="return-book.jsp">Return a book</a>
    <a href="register-student.jsp">Register new student</a>
    <a href="add-book.jsp">Add new book</a>
</nav>

<form action="search.jsp" method="get">

    <h1>Serach for books</h1>

    <label for="category">Select category:</label>
    <select name="category" id="category">
        <option value="all" selected>All</option>
        <%
            for (Category category : CategoriesRepository.getInstance().getAll()) {
                out.println("<option>" + category.name + "</option>");
            }
        %>
    </select>

    <label for="title">Enter Title: </label>
    <input type="text" name="title" id="title" placeholder="title" value="<%= Optional.ofNullable(request.getParameter("title")).orElse("") %>">

    <input type="submit" value="Search">

</form>

<%
    boolean isPostBack = request.getParameter("title") != null || request.getParameter("category") != null;

    String title = Optional.ofNullable(request.getParameter("title")).orElse("*");
    String category = Optional.ofNullable(request.getParameter("category")).orElse("all");

    if (isPostBack) out.println("<h1>Results</h1>");
%>

<ul>

    <%

        if (isPostBack) {

            List<Book> books = BooksRepository
                    .getInstance()
                    .findBookByName(title)
                    .filter(book -> category.equalsIgnoreCase("all") || book.category.equalsIgnoreCase(category))
                    .collect(Collectors.toList());


            if (books != null && books.size() > 0) {

                Iterator<Book> iterator = books.iterator();

                while (iterator.hasNext()) {
                    Book next = iterator.next();

                    out.println("<li>" + next.name + " - " + next.category + "</li>");
                }
            } else {
                out.println("no books found");
            }
        }
    %>
</ul>

</body>

</html>