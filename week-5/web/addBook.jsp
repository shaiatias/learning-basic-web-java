

<%@page import="java.util.Optional"%>
<%@page import="repositories.CategoriesRepository"%>
<%@page import="models.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        
        <form method="post" action="AddBook">

            <h1>Add New Book</h1>

            <label for="bookName">Book Name: </label>
            <input type="text" name="bookName" id="bookName" placeholder="book name" value="<%= Optional.ofNullable(request.getParameter("bookName")).orElse("")%>">
            <br/>
            <label for="category">Select category:</label>
            <select name="category" id="category">
                <option selected></option>
                <%
                    for (Category category : CategoriesRepository.getInstance().getAll()) {
                        out.println("<option>" + category.name + "</option>");
                    }
                %>
            </select>
            <br/>
            <label for="author">Author: </label>
            <input type="text" name="author" id="author" placeholder="author" value="<%= Optional.ofNullable(request.getParameter("author")).orElse("")%>">
            <br/>    
            <label for="isbn">ISBN: </label>
            <input type="text" id="isbn" name="isbn" placeholder="book's isbn" value="<%= Optional.ofNullable(request.getParameter("isbn")).orElse("")%>">
            <br/>
            <label for="publicationYear">Year of publication: </label>
            <input type="number" min="1900" name="publicationYear" max="2018" id="publicationYear" value="<%= Optional.ofNullable(request.getParameter("year")).orElse("")%>">
            <br/>    
            <label for="copies">Copies: </label>
            <input type="number" min="1" max="100" name="copies" id="copies" value="<%= Optional.ofNullable(request.getParameter("copies")).orElse("")%>">
            <br/>
            <input type="submit" value="Submit">

        </form>
            
    </body>

</html>