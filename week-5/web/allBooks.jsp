
<%@page import="models.Book"%>
<%@page import="helpers.BeanClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean type="helpers.BeanClass" id="results" beanName="results" scope="request" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        <h1>Books</h1>
        
        <ul>
        <%
            for (Book b: results.searchResults) {
                out.println("<li>");
                out.println(b.name);
                out.println("</li>");
            }        
        %>
        </ul>
        
    </body>
</html>

