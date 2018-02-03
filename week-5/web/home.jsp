<%-- 
    Document   : home
    Created on : 01-Feb-2018, 20:51:58
    Author     : Manage Your Trip
--%>

<%@page import="java.util.Optional"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="iframe-styles.css"/>
    </head>
    <body>
        <h1>Welcome <%= Optional.ofNullable(session.getAttribute("username")).orElse("Guest")%>!</h1>
        <small>last login was in <%= Optional.ofNullable(session.getAttribute("lastLoginTime")).orElse("")%></small>
    </body>
</html>
