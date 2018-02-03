<%-- 
    Document   : success
    Created on : Feb 3, 2018, 12:32:03 AM
    Author     : shai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%= request.getAttribute("message") %></h1>
    </body>
</html>
