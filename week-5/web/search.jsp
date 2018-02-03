<%-- 
    Document   : search
    Created on : Feb 1, 2018, 5:57:58 PM
    Author     : shai
--%>

<%@page import="java.util.Optional"%>
<%@page import="helpers.BeanClass"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="results" scope="session" class="helpers.BeanClass" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        
        <h1>Search book</h1>
        
        <form action="Search" method="POST">
            <label>Book name: </label>
            <input name="bookName" value="<%= Optional.ofNullable(request.getParameter("bookName")).orElse("")%>" autofocus />
            <br />

            <label>Category: </label>
            <select name="category">
                <option>all</option>
                <%= request.getAttribute("optionsString") %>
            </select>
            <br />

            <input type="submit" value="Search" />
        </form>

        <%
            if (results.searchResults != null && results.searchResults.size() == 0) {
                out.println("<h2>no book were found</h2>");
            }
        %>
            
        <%
            if (results.searchResults != null) {
        %>

        <ul>
            <%
                for (models.Book book : results.searchResults) {
                    out.println("<li>" + book.name + "</li>");
                }
            %>
        </ul>

        <%
            }
        %>

    </body>
</html>
