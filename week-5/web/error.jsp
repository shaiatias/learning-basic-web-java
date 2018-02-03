<%-- 
    Document   : error
    Created on : Feb 2, 2018, 5:58:50 PM
    Author     : shai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        <h1>Error</h1>
        <p><%= request.getAttribute("error") %></p>
    </body>
</html>
